package com.domgarr.tacoloco;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static com.domgarr.tacoloco.service.MenuService.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.domgarr.tacoloco.model.MenuItem;
import com.domgarr.tacoloco.model.OrderItem;



public class OrderItemTests extends TacoLocoApplicationTests {
	
	private OrderItem veggieTacoOrder;
	
	@BeforeEach
	void init() {
		MenuItem veggieTaco = new MenuItem(1, VEGGIE_TACO, 2.50);
		veggieTacoOrder = new OrderItem(veggieTaco, 1);
	}
	
	//Test getQuantity()
	@Test
	@DisplayName("getQauntity() should return a quantity of 1 as an integer.")
	void orderItemPositiveQuantityTest() {
		assertEquals(1, veggieTacoOrder.getQuantity());
	}
	
	//Test setQuantity
	
	/* If a customer changes their mind and wants to change their order remove the OrderItem
	from this list kept in Order. With that said, setting the Quantity of an ItemOrder to 0
	is not a possible outcome.
	*/
	
	@Test
	@DisplayName("setQuantity(2) should set the value of quantity to 2")
	void orderItemSetQuantityTwoTest() {
		veggieTacoOrder.setQuantity(2);
		assertEquals(2, veggieTacoOrder.getQuantity());
	}
	
	@Test
	@DisplayName("setQuantity(0) should return an InvalidArgumentException.")
	void setZeroQuantityTwoTest() {
		Exception exception = assertThrows(IllegalArgumentException.class,() -> {veggieTacoOrder.setQuantity(0);});
		assertEquals( "The quantity of an order can't be less than or equal 0.", exception.getMessage());
	}

	@Test
	@DisplayName("setQuantity(-1) should return an InvalidArgumentException.")
	void setNegativeQuantityTest() {
		Exception exception = assertThrows(IllegalArgumentException.class,() -> {veggieTacoOrder.setQuantity(-1);});
		assertEquals("The quantity of an order can't be less than or equal 0.", exception.getMessage());
	}

	//Test OrderItem(menuItem, quantity)
	@Test
	@DisplayName("The OrderItem constructor should take two params: object: MenuItem.class and quantity: integer.")
	void orderItemConstructorTest() {
		assertNotNull(veggieTacoOrder.getMenuItem());
		assertEquals(1, veggieTacoOrder.getQuantity());
	}
	
	//Test getMenuItem()
	@Test
	@DisplayName("GetMenuItem() should return a MenuItem object")
	void getMenuItemTest() {
		assertNotNull(veggieTacoOrder.getMenuItem());
	}
	
	//Test setMenuItem()
	@Test
	@DisplayName("Setting a menuItem should mutate the menuItem object stored in OrderItem")
	void setMenuItemMutateMenuItemTest() {
		MenuItem chickenTaco = new MenuItem(1, CHICKEN_TACO, 3);
		assertNotNull(chickenTaco);
		
		veggieTacoOrder.setMenuItem(chickenTaco);
		assertEquals(chickenTaco, veggieTacoOrder.getMenuItem());
	}
	
	@Test
	@DisplayName("Setting a null menuItem should throw a IllegalArgumentException")
	void setMenuItemNullArgument() {
		MenuItem chickenTaco = null;
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {veggieTacoOrder.setMenuItem(chickenTaco);});
		assertEquals("MenuItem can't have a null value.", exception.getMessage() );
		
	}
	
	//Test getTotalCost()
	@Test
	@DisplayName("An OrderItem with menuItem veggieTaco and quantity 1 should have a total cost of 2.50")
	void orderItemWithQuantityOfOneTotalCostTest() {
		assertEquals(1, veggieTacoOrder.getQuantity());
		//By default the veggieTacoOrder has a quantity of 1.
		assertEquals(2.50, veggieTacoOrder.getTotalCost());
	}
	
	@Test
	@DisplayName("An OrderItem with menuItem veggieTaco and quantity of 2 total cost should be 5.00")
	void orderItemWithQuantityOfTwoTotalCostTest() {
		veggieTacoOrder.setQuantity(2);
		assertEquals(2, veggieTacoOrder.getQuantity());
		assertEquals(5.00, veggieTacoOrder.getTotalCost());
	}
	
}
