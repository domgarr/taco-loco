package com.domgarr.tacoloco;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.domgarr.tacoloco.model.MenuItem;
import com.domgarr.tacoloco.model.OrderItem;
import com.domgarr.tacoloco.model.OrderItemRequest;
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
		Exception exception = assertThrows(IllegalArgumentException.class, ()->{orderService.getTotalOrderCostFromOrderItems(nullOrderList);});
		assertEquals("The list of item orders can't be null or empty.", exception.getMessage());
	}
	
	@Test
	@DisplayName("An empty OrderItemArrayList should throw an IllegalArgumentException")
	void getTotalOrderCostEmptyArrayListTest() {
		Exception exception = assertThrows(IllegalArgumentException.class, ()->{orderService.getTotalOrderCostFromOrderItems(orderItems);});
		assertEquals("The list of item orders can't be null or empty.", exception.getMessage());
	}
	
	//Test getTotalOrderCost()
	//By default all orders have 1 quantity.
	
	@Test
	@DisplayName("1 veggie taco order should return 2.50")
	void getTotalOrderCostOneItemTest() {
		orderItems.add(veggieTacoOrder);
		
		assertEquals(2.50, orderService.getTotalOrderCostFromOrderItems(orderItems));
	}
	
	@Test
	@DisplayName("1 veggie & 1 chicken taco order a should return 5.50")
	void getTotalOrderCost2Test() {
		orderItems.add(veggieTacoOrder);
		orderItems.add(chickenTacoOrder);

		assertEquals(5.50, orderService.getTotalOrderCostFromOrderItems(orderItems));
	}
	
	@Test
	@DisplayName("1 veggie, 1 chicken & 1 chorizo taco order a should return 9.00")
	void getTotalOrderCost3Test() {
		orderItems.add(veggieTacoOrder);
		orderItems.add(chickenTacoOrder);
		orderItems.add(chorizoTacoOrder);
		
		assertEquals(9.00, orderService.getTotalOrderCostFromOrderItems(orderItems));
	}
	
	//Test exactly 4 items.
	@Test
	@DisplayName("1 veggie, 1 chicken, 1 beef & 1 chorizo taco order a should return 9.60 - discount applied")
	void getTotalOrderCost4Test() {
		orderItems.add(veggieTacoOrder);
		orderItems.add(beefTacoOrder);
		orderItems.add(chickenTacoOrder);
		orderItems.add(chorizoTacoOrder);
		
		assertEquals(9.60, orderService.getTotalOrderCostFromOrderItems(orderItems));
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
		
		assertEquals(19.20, orderService.getTotalOrderCostFromOrderItems(orderItems));
	}
	
	@Test
	@DisplayName("An OrderItemRequest with id: 1 and quantity of 1 should equate to a veggie taco OrderItem in an ArrayList.")
	void toOrderItemListTest(){
		OrderItemRequest orderItemReq = new OrderItemRequest(1,1);
		ArrayList<OrderItemRequest> orderItemReqList = new ArrayList<>();
		orderItemReqList.add(orderItemReq);
		
		ArrayList<OrderItem> orderItemList = orderService.toOrderItemList(orderItemReqList);
		
		assertNotNull(orderItemList);
		assertEquals(1, orderItemList.size());
		OrderItem veggieTaco = orderItemList.get(0);
		
		assertEquals(1, veggieTaco.getMenuItem().getId());
		assertEquals(new BigDecimal(2.50).setScale(2, RoundingMode.HALF_UP), veggieTaco.getMenuItem().getCost());
		assertEquals(VEGGIE_TACO, veggieTaco.getMenuItem().getName());
		assertEquals(1, veggieTaco.getQuantity());
	}
	
	@Test
	@DisplayName("An OrderItemRequest(2,2), An OrderItemRequest(3,3) OrderItemRequest(4,3) should be a itemOrder of Chicken, Beef & Chorizo Taco respectively")
	void toOrderItemListMultipleTest() {
		OrderItemRequest orderItemReq1 = new OrderItemRequest(2,2);
		OrderItemRequest orderItemReq2 = new OrderItemRequest(3,3);
		OrderItemRequest orderItemReq3 = new OrderItemRequest(4,3);
		
		ArrayList<OrderItemRequest> orderItemReqList = new ArrayList<>();
		orderItemReqList.add(orderItemReq1);
		orderItemReqList.add(orderItemReq2);
		orderItemReqList.add(orderItemReq3);
		
		ArrayList<OrderItem> orderItemList = orderService.toOrderItemList(orderItemReqList);
		
		assertNotNull(orderItemList);
		assertEquals(3, orderItemList.size());

		//Chicken Taco -> Id: 2, Quantity: 2
		OrderItem taco = orderItemList.get(0);
		assertEquals(2, taco.getMenuItem().getId());
		assertEquals(CHICKEN_TACO,taco.getMenuItem().getName());
		assertEquals(2, taco.getQuantity());
		
		//Beef Taco -> Id: 3, Quantity: 3
		taco = orderItemList.get(1);
		assertEquals(3, taco.getMenuItem().getId());
		assertEquals(BEEF_TACO,taco.getMenuItem().getName());
		assertEquals(3, taco.getQuantity());
		
		//Beef Taco -> Id: 4, Quantity: 3
		taco = orderItemList.get(2);
		assertEquals(4, taco.getMenuItem().getId());
		assertEquals(CHORIZO_TACO,taco.getMenuItem().getName());
		assertEquals(3, taco.getQuantity());
	}
	
	@Test
	@DisplayName("An OrderItemRequest with id: 7 and quantity of 1 should throw an IllegalArgumentException stating: 'The given id of 7 is not a valid id.'")
	void toOrderItemListTestIncorrectIdTest(){
		OrderItemRequest orderItemReq = new OrderItemRequest(7,1);
		ArrayList<OrderItemRequest> orderItemReqList = new ArrayList<>();
		orderItemReqList.add(orderItemReq);
		
		Exception exception = assertThrows(IllegalArgumentException.class, ()->{orderService.toOrderItemList(orderItemReqList);});
		assertEquals("The given id of 7 is not a valid id for a menu item.", exception.getMessage());
	}
}
