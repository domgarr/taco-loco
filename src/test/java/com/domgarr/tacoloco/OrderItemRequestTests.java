package com.domgarr.tacoloco;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.domgarr.tacoloco.model.OrderItemRequest;

public class OrderItemRequestTests extends TacoLocoApplicationTests{
	public OrderItemRequest orderItemReq;
	
	@BeforeEach
	void init(){
		orderItemReq = new OrderItemRequest();
	}
	
	@Test
	@DisplayName("OrderItemRequest(Integer id = 1, Integer quantity = 1) should set id and quantity to equal 1")
	void testOrderItemRequestConstructor() {
		orderItemReq = new OrderItemRequest(1,1);
		assertEquals(1, orderItemReq.getId());
		assertEquals(1, orderItemReq.getQuantity());
	}
	
	//Note that the tests begin with a null Id and Quantity values in the object OrderItemReq.
	
	@Test
	@DisplayName("Getting the id of OrderItemRequest which is set 1 should return 1 after setting the id to 1.")
	void testSetOrderItemRequestSetId1() {
		orderItemReq.setId(1);
		assertEquals(1, orderItemReq.getId());
	}
	
	
	@Test
	@DisplayName("Getting the id of OrderItemRequest which is set to null should throw a IllegalArgumentException with the message: 'Item ordered is missing an Id.'")
	void testSetOrderItemRequestSetId2() {
		Exception exception = assertThrows(IllegalArgumentException.class, ()->{orderItemReq.getId();});
		assertEquals("Item ordered is missing an Id.", exception.getMessage());
	}
	
	@Test
	@DisplayName("Getting the quantity of OrderItemRequest shoulbe 1 after setting the quantity of the object to 1.")
	void testSetOrderItemRequestSetQuantity1() {
		orderItemReq.setQuantity(1);
		assertEquals(1, orderItemReq.getQuantity());
	}
	
	
	@Test
	@DisplayName("Get the quantity of OrderItemRequest which is set to null should throw a IllegalArgumentException with the message: 'Item ordered is missing a quantity.'.")
	void testSetOrderItemRequestSetQuantity2() {
		Exception exception = assertThrows(IllegalArgumentException.class, ()->{orderItemReq.getQuantity();});
		assertEquals("Item ordered is missing a quantity.", exception.getMessage());
	}
	
	
}
