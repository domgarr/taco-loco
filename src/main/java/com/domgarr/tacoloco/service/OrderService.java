package com.domgarr.tacoloco.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domgarr.tacoloco.model.MenuItem;
import com.domgarr.tacoloco.model.OrderItem;
import com.domgarr.tacoloco.model.OrderItemRequest;

@Service
public class OrderService {
	@Autowired
	private MenuService menuService;
	
	private int discountThreshold = 4;
	private double discountPrecentage = 0.20;
	
	/**
	 * 
	 * @param orderItemReqList
	 * @return totalCost/sum of OrderItems.
	 */
	public double getTotalCost(ArrayList<OrderItemRequest> orderItemReqList) {
		return getTotalOrderCostFromOrderItems(toOrderItemList(orderItemReqList));
	}
	
	/**
	 * Given an ArrayList comprised OrderItems, calculate the total cost of the entire order
	 * and apply a discount if applicable. 
	 *
	 * 
	 * @param orderItemList of OrderItems.
	 * @return double representing total cost of entire order rounded to two decimal points.
	 */
	public double getTotalOrderCostFromOrderItems(ArrayList<OrderItem> orderItemList) {
		double totalCost = 0;
		int totalQuantity = 0;
		
		for(OrderItem orderItem : orderItemList) {
			totalCost += orderItem.getTotalCost().doubleValue();
			totalQuantity += orderItem.getQuantity();
		}
		
		//Check if discount applies.
		if(totalQuantity >= discountThreshold) {
			totalCost *= 1 - discountPrecentage;
		}
		//Round totalCost to two decimal places.
		return new BigDecimal(totalCost).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}

	/**
	 * OrderController takes a list of OrderItemRequest objects (containing id and quantity - the minimum amount of info needed to calculate cost)
	 * which we have to then populate into OrderItem objects to use as an input for getTotalOrderCost().
	 * 
	 * Note I have moved ErrorHandling to this method, since it's executed first. If any of the checks fail
	 * in this method, there is no reason to go further in calculating the totalCost.
	 * 
	 * Something to think about, should this method be made public for testing purposes or made private and getTotalCost() will include all tests?
	 * 
	 * @param orderItemReqList - list containing OrderItemRequests
	 * @return ArrayList<OrderItems>
	 * @throws IllegalArgumentException
	 */
	public ArrayList<OrderItem> toOrderItemList(ArrayList<OrderItemRequest> orderItemReqList) throws IllegalArgumentException{
		//Need the HashMap containing the Integer key to map OrderItemRequest id's to menu item ids.
		HashMap<Integer, MenuItem> menuItems = menuService.getMenuMapWithIntegerAsKey();
		checkForEmptyNullOrOversizedList(orderItemReqList, menuItems);
		//Used for duplicate menu item id check.
		HashMap<Integer, Integer> idMap = new HashMap<>();
		
		ArrayList<OrderItem> orderItems = new ArrayList<>();
		for(OrderItemRequest orderItemReq : orderItemReqList) {
			int orderItemReqId = orderItemReq.getId();
			checkForDuplicateAndInvalidMenuItems(orderItemReqId, idMap, menuItems);
			
			//add Id to mapId and if ID already exists continue, else exit.
		
			OrderItem orderItem = new OrderItem();
			orderItem.setMenuItem(menuItems.get(orderItemReq.getId()));
			orderItem.setQuantity(orderItemReq.getQuantity());
			
			orderItems.add(orderItem);
		}
		return orderItems;
	}

	private void checkForEmptyNullOrOversizedList(ArrayList<OrderItemRequest> orderItemReqList, HashMap<Integer, MenuItem> menuItems ) {
		if(orderItemReqList == null || orderItemReqList.isEmpty()) {
			throw new IllegalArgumentException("The list of item orders can't be null or empty.");
		}else if(orderItemReqList.size() > menuItems.size()) {
			throw new IllegalArgumentException("The number of objects in the JSON request have exceeded the allowable length.");
		}
	}
	
	private void checkForDuplicateAndInvalidMenuItems(int orderItemReqId, HashMap<Integer, Integer> idMap, HashMap<Integer, MenuItem> menuItems ) throws IllegalArgumentException {
		if(!menuItems.containsKey(orderItemReqId) ) {
			throw new IllegalArgumentException("The given id of " + orderItemReqId + " is not a valid id for a menu item.");
		}
		
		//Use a HashMap to check for existing keys. 
		if(idMap.containsKey(orderItemReqId)) {
			throw new IllegalArgumentException("Duplicate menu items in the request found.");
		}else {
			idMap.put(orderItemReqId, orderItemReqId);
		}
		
	}
}
