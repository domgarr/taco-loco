package com.domgarr.tacoloco.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * OrderItem will encapsulate a menuItem and the quantity ordered. 
 * The main purpose of this class is to calculate the totalCost of a menuItem with n quantity selected. 
 * 
 * 
 * @author Domenic
 *
 */

public class OrderItem {
	private MenuItem menuItem;
	private int quantity;
	
	public OrderItem() {
		
	}
	
	public OrderItem(MenuItem menuItem, int quantity) {
		setMenuItem(menuItem);
		setQuantity(quantity);
	}

	public MenuItem getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(MenuItem menuItem) {
		if(menuItem == null) {
			throw new IllegalArgumentException("MenuItem can't have a null value.");
		}else {
			this.menuItem = menuItem;
		}
	}

	public int getQuantity() {
		return quantity;
	}

	/**
	 * I've decided to not allow quantities of zero. Instead simply remove the MenuItem for the list of OrderItems.
	 * 
	 * @param quantity: greater than or equal to 1.
	 */
	public void setQuantity(int quantity) {
		
		if(quantity <= 0) {
			throw new IllegalArgumentException("The quantity of an order can't be less than or equal 0.");
		}else if(quantity > 100) {
			throw new IllegalArgumentException("The quantity of a single item order can't exceed 100.");
		}else {
			this.quantity = quantity;
		}
	}
	
	public BigDecimal getTotalCost() {
		return new BigDecimal(menuItem.getCost().doubleValue() * quantity).setScale(2, RoundingMode.HALF_UP );
	}
	
	
	
	
	
}
