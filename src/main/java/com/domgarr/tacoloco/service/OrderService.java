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
	 * @param orderItemList of OrderItems.
	 * @return double representing total cost of entire order rounded to two decimal points.
	 */
	public double getTotalOrderCostFromOrderItems(ArrayList<OrderItem> orderItemList) {
		
		if(orderItemList == null || orderItemList.isEmpty()) {
			throw new IllegalArgumentException("The list of item orders can't be null or empty.");
		}
		
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
	 * @param orderItemReqList - list containing OrderItemRequests
	 * @return ArrayList<OrderItems>
	 * @throws IllegalArgumentException
	 */
	public ArrayList<OrderItem> toOrderItemList(ArrayList<OrderItemRequest> orderItemReqList) throws IllegalArgumentException{
		//Need the HashMap containing the Integer key to map OrderItemRequest id's to menu item ids.
		HashMap<Integer, MenuItem> menuItems = menuService.getMenuMapWithIntegerAsKey();
		
		ArrayList<OrderItem> orderItems = new ArrayList<>();
		for(OrderItemRequest orderItemReq : orderItemReqList) {
			int orderItemReqId = orderItemReq.getId();
			//If the client sends an unknown id throw an error.
			if(!menuItems.containsKey(orderItemReqId) ) {
				throw new IllegalArgumentException("The given id of " + orderItemReqId + " is not a valid id for a menu item.");
			}
		
			OrderItem orderItem = new OrderItem();
			orderItem.setMenuItem(menuItems.get(orderItemReq.getId()));
			orderItem.setQuantity(orderItemReq.getQuantity());
			
			orderItems.add(orderItem);
		}
		return orderItems;
	}
}
