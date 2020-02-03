package com.domgarr.tacoloco;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.domgarr.tacoloco.model.MenuItem;
import com.domgarr.tacoloco.model.OrderItem;
import com.domgarr.tacoloco.service.MenuService;
import com.domgarr.tacoloco.service.OrderService;

import static com.domgarr.tacoloco.service.MenuService.*;

public class OrderServiceTest extends TacoLocoApplicationTests {
	@Autowired
	private OrderService orderService;
	@Autowired
	private MenuService menuService;
	
	private ArrayList<OrderItem> orderItems;
		
	private OrderItem veggieTacoOrder;
	private OrderItem chickenTacoOrder;
	private OrderItem beefTacoOrder;
	private OrderItem chorizoTacoOrder;
	
	@BeforeEach
	void init() {
		HashMap<String, MenuItem> menuItems = menuService.getMenuMapWithStringAsKey();
		
		veggieTacoOrder = new OrderItem(menuItems.get(VEGGIE_TACO), 1);
		chickenTacoOrder = new OrderItem(menuItems.get(CHICKEN_TACO), 1);
		beefTacoOrder = new OrderItem(menuItems.get(BEEF_TACO), 1);
		chorizoTacoOrder = new OrderItem(menuItems.get(CHORIZO_TACO), 1);
		
		orderItems = new ArrayList<>();
	}
	
	//Test if the ArrayList is null or empty;
	@Test
	@DisplayName("A null OrderItemArrayList should throw an IllegalArgumentException")
	void getTotalOrderCostNullArrayListTest() {
		ArrayList<OrderItem> nullOrderList = null;
		Exception exception = assertThrows(IllegalArgumentException.class, ()->{orderService.getTotalOrderCost(nullOrderList);});
		assertEquals("ArrayList of OrderItems can't be null or empty.", exception.getMessage());
	}
	
	@Test
	@DisplayName("An empty OrderItemArrayList should throw an IllegalArgumentException")
	void getTotalOrderCostEmptyArrayListTest() {
		Exception exception = assertThrows(IllegalArgumentException.class, ()->{orderService.getTotalOrderCost(orderItems);});
		assertEquals("ArrayList of OrderItems can't be null or empty.", exception.getMessage());
	}
	
	//Test getTotalOrderCost()
	//By default all orders have 1 quantity.
	
	@Test
	@DisplayName("1 veggie taco order should return 2.50")
	void getTotalOrderCostOneItemTest() {
		orderItems.add(veggieTacoOrder);
		
		assertEquals(2.50, orderService.getTotalOrderCost(orderItems));
	}
	
	@Test
	@DisplayName("1 veggie & 1 chicken taco order a should return 5.50")
	void getTotalOrderCost2Test() {
		orderItems.add(veggieTacoOrder);
		orderItems.add(chickenTacoOrder);

		assertEquals(5.50, orderService.getTotalOrderCost(orderItems));
	}
	
	@Test
	@DisplayName("1 veggie, 1 chicken & 1 chorizo taco order a should return 9.00")
	void getTotalOrderCost3Test() {
		orderItems.add(veggieTacoOrder);
		orderItems.add(chickenTacoOrder);
		orderItems.add(chorizoTacoOrder);
		
		assertEquals(9.00, orderService.getTotalOrderCost(orderItems));
	}
	
	//Test exactly 4 items.
	@Test
	@DisplayName("1 veggie, 1 chicken, 1 beef & 1 chorizo taco order a should return 9.60 - discount applied")
	void getTotalOrderCost4Test() {
		orderItems.add(veggieTacoOrder);
		orderItems.add(beefTacoOrder);
		orderItems.add(chickenTacoOrder);
		orderItems.add(chorizoTacoOrder);
		
		assertEquals(9.60, orderService.getTotalOrderCost(orderItems));
	}
	
	//Test greater than 8 items.
	@Test
	@DisplayName("2 veggie, 2 chicken, 2 beef & 2 chorizo taco order a should return 19.20 - discount applied")
	void getTotalOrderCost8ItemTest() {
		veggieTacoOrder.setQuantity(2);
		orderItems.add(veggieTacoOrder);
		
		beefTacoOrder.setQuantity(2);
		orderItems.add(beefTacoOrder);
		
		chickenTacoOrder.setQuantity(2);
		orderItems.add(chickenTacoOrder);
		
		chorizoTacoOrder.setQuantity(2);
		orderItems.add(chorizoTacoOrder);
		
		assertEquals(19.20, orderService.getTotalOrderCost(orderItems));
	}
	
	
	//Test roundTotalCost()
	
	@Test
	@DisplayName("roundTotalCost(2.2) should return 2.22")
	void roundDoubleToTwoDecimalPointsOneDecPointTest() {
		assertEquals(2.20, orderService.roundDoubleToTwoDecimalPoints(2.2));
	}
	
	@Test
	@DisplayName("roundTotalCost(2.22) should return 2.22")
	void roundDoubleToTwoDecimalPointsTwoDecPointTest() {
		assertEquals(2.22, orderService.roundDoubleToTwoDecimalPoints(2.22));
	}
	
	@Test
	@DisplayName("roundTotalCost(2.25) should return 2.30")
	void roundDoubleToTwoDecimalPointsTwoDecPointRoundUpTest() {
		assertEquals(2.20, orderService.roundDoubleToTwoDecimalPoints(2.2));
	}
	
	@Test
	@DisplayName("roundTotalCost(2.223333456) should return 2.22")
	void roundDoubleToTwoDecimalPoints9DecPointTest() {
		assertEquals(2.22, orderService.roundDoubleToTwoDecimalPoints(2.223333456));
	}

}
