package com.domgarr.tacoloco.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.domgarr.tacoloco.model.OrderItemRequest;
import com.domgarr.tacoloco.service.OrderService;



@RestController
public class OrderController {
	@Autowired
	private OrderService orderService;
		
	/**
	 * Given a list of OrderItemRequests containing the minimum amount of info needed to create
	 * OrderItems (objects containing totalCost of a Menu Item and quantity ordered) return a JSON
	 * with the totalCost of all menu items ordered.
	 * 
	 * @param ArrayList<OrderItemReq> 
	 * @return a JSON containing the field totalCost.
	 */
	@RequestMapping(value="/order", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public  HashMap<String, Object> postOrder(@RequestBody ArrayList<OrderItemRequest> orderItemReqList) {
		try {
		double totalCost = orderService.getTotalCost(orderItemReqList);
		//HashMap is used to create the JSON response.
		HashMap<String, Object> response = new HashMap<>();
		//Rounding of totalCost is lost when the double return by orderService is wrapped into Double.class. BigDecimal() worked as a solution to this.
		response.put("totalCost", new BigDecimal(totalCost).setScale(2, RoundingMode.HALF_UP));
		return response;
		}catch(IllegalArgumentException exc) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exc.getMessage(), exc);
		}
	}
}
