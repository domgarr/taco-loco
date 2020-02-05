package com.domgarr.tacoloco.model;

/**
 * OrderItemRequest is used to map a JSON object containing an id and quantity.
 * OrderService will use OrderItemRequest's state to populate OrderItem objects.
 * 	- id is used by OrderService to find the MenuItems information.
 * 
 * @author Domenic
 *
 */
public class OrderItemRequest {
	private Integer id;
	private Integer quantity;

	public OrderItemRequest() {
	}
	
	public OrderItemRequest(int id, int quantity) {
		setId(id);
		setQuantity(quantity);
	}

	//Since I am using wrapper classes for int, I decided to only check the getters() for null values.
	public Integer getId() {
		if(id == null) {
			throw new IllegalArgumentException("Item ordered is missing an Id.");
		}else {
		return id;
		}
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQuantity() {
		if(quantity == null) {
			throw new IllegalArgumentException("Item ordered is missing a quantity.");

		}else{
			return quantity;
		}
	}

	public void setQuantity(Integer quantity) {
		
		this.quantity = quantity;
	}
	
	
	
	
}
