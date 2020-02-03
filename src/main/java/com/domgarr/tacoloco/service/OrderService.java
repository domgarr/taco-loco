package com.domgarr.tacoloco.service;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.domgarr.tacoloco.model.OrderItem;

@Component
public class OrderService {
	
	private int discountThreshold = 4;
	private double discountPrecentage = 0.20;
	
	/**
	 * Given an ArrayList comprised OrderItems, calculate the total cost of the entire order
	 * and apply a discount if applicable. 
	 * 
	 * @param orderItemList of OrderItems.
	 * @return double representing total cost of entire order rounded to two decimal points.
	 */
	public double getTotalOrderCost(ArrayList<OrderItem> orderItemList) {
		
		if(orderItemList == null || orderItemList.isEmpty()) {
			throw new IllegalArgumentException("ArrayList of OrderItems can't be null or empty.");
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
	 * @param totalCost: double
	 * @return A double rounded to two decimal points.
	 */
	public double roundDoubleToTwoDecimalPoints(double totalCost) {
		DecimalFormat df = new DecimalFormat("#.00");
		return Double.valueOf(df.format(totalCost));
	}
}
