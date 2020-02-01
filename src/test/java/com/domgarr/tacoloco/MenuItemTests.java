package com.domgarr.tacoloco;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.domgarr.tacoloco.model.MenuItem;

public class MenuItemTests extends TacoLocoApplicationTests {
	private MenuItem veggieTaco;
	
	
	@BeforeEach
	void init() {
		veggieTaco = new MenuItem("Veggie Taco", 2.50);
	}
	
	//Test constructor.
	@Test
	@DisplayName("The MenuItem constructor should take a name followed by a cost")
	void menuItemTwoParameterConstructorTest() {
		assertEquals("Veggie Taco", veggieTaco.getName());
		assertEquals(2.50, veggieTaco.getCost());
	}

	//test setCost()
	@Test
	@DisplayName("Setting the cost of a MenuItem should mutate the cost.")
	void menuItemSetCostWithValidValueTest() {
		assertEquals(2.50, veggieTaco.getCost());
		veggieTaco.setCost(3.00);
		assertEquals(3.00, veggieTaco.getCost());
	}
	
	@Test
	@DisplayName("Setting the cost to zero is an invalid argument value.")
	void menuItemSetCostWithInvalidValueZero(){
		Exception exception = assertThrows(IllegalArgumentException.class, ()->{
			veggieTaco.setCost(0);
		});
		assertEquals("Zero or negative doubles are an invalid argument for cost.", exception.getMessage());
	}
	
	@Test
	@DisplayName("Setting the cost to a negative dobule is an invalid argument value.")
	void menuItemSetCostWithInvalidValueNegOne(){
		Exception exception = assertThrows(IllegalArgumentException.class, ()->{
			veggieTaco.setCost(-1);
		});
		assertEquals("Zero or negative doubles are an invalid argument for cost.", exception.getMessage());
	}
	
	//test setName();
	@Test
	@DisplayName("Setting the menuItems name should mutate the name.")
	void menuItemSetNameWithValidString() {
		assertEquals("Veggie Taco", veggieTaco.getName());
		veggieTaco.setName("Beef Taco");
		assertEquals("Beef Taco", veggieTaco.getName());
	}
	
	@Test
	@DisplayName("Setting the menuItems name with an empty string should throw an IllegalArgumentExpection.")
	void menuItemSetNameWithEmptyString() {
		assertEquals("Veggie Taco", veggieTaco.getName());
		Exception exception =  assertThrows(IllegalArgumentException.class, () -> {
			veggieTaco.setName("");
		});
		assertEquals("An empty String is an invalid name.", exception.getMessage());
	}
	
}
