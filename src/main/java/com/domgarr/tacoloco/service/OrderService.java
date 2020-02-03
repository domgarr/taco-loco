package com.domgarr.tacoloco.service;

import java.text.DecimalFormat;
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
			totalCost += orderItem.getTotalCost();
			totalQuantity += orderItem.getQuantity();
		}
		
		//Check if discount applies.
		if(totalQuantity >= discountThreshold) {
			totalCost *= 1 - discountPrecentage;
		}

		return roundDoubleToTwoDecimalPoints(totalCost);
	}

	/**
	 * If a double has less than 2 decimal points append 0's to create to decimal points.
	 * 
	 * The purpose of this method is to have cost output consistency of two decimal places. 
	 * 
	 * @param totalCost: double
	 * @return A double rounded to two decimal points.
	 */
	public double roundDoubleToTwoDecimalPoints(double totalCost) {
		//By using this format a double of value 2.2 will be return as in 2.20 and 2.222222 will be rounded to two decimal places : 2.22
		DecimalFormat df = new DecimalFormat("#.00");
		return Double.valueOf(df.format(totalCost));
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
