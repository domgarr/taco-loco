package com.domgarr.tacoloco.model;

/**
 * MenuItem is a model for an item thats exists on taco-loco's menu.
 * This model will be wrapped by the OrderItem class where it can be used
 * to calculate the total cost based off a given quantity. 
 * 
 * @author Domenic
 *
 */

public class MenuItem {
	int id;
	private String name;
	private double cost;
	
	public MenuItem(int id, String name, double cost) {
		setId(id);
		setName(name);
		setCost(cost);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if(!name.isEmpty()) {
			this.name = name;
		}else {
			throw new IllegalArgumentException("An empty String is an invalid name.");
		}
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		
		if(cost <= 0) {
			throw new IllegalArgumentException("Zero or negative doubles are an invalid argument for cost.");
		}else {
			this.cost = cost;
		}
	}	
}
