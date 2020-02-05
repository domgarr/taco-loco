package com.domgarr.tacoloco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@AutoConfigureMockMvc
public class OrderControllerTest extends TacoLocoApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	
	private final String pathToJsonRequests = Paths.get("").toAbsolutePath() + "/src/test/java/com/domgarr/json_requests";
	
	@Test
	@DisplayName("1 Veggie Taco order should return a Json with atotalCost : 2.50")
	void orderControllerPostOrderTest1() throws Exception {
	    String jsonContent = new String(Files.readAllBytes(Paths.get(pathToJsonRequests + "/test_1.json")));

	    this.mockMvc.perform(post("/order").content(jsonContent).contentType(MediaType.APPLICATION_JSON_VALUE))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("{\"totalCost\":2.50}")));
	}
	
	@Test
	@DisplayName("2 Chicken Taco order should return a Json with a totalCost : 6")
	void orderControllerPostOrderTest2() throws Exception {
		
		
	    String jsonContent = new String(Files.readAllBytes(Paths.get(pathToJsonRequests + "/test_2.json")));

	    this.mockMvc.perform(post("/order").content(jsonContent).contentType(MediaType.APPLICATION_JSON_VALUE))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("{\"totalCost\":6.00}")));
	}
	
	@Test
	@DisplayName("A order of each taco with one quantity each should equal 9.60 (Discount applied) ")
	void orderControllerPostOrderTest3() throws Exception {
		
		
	    String jsonContent = new String(Files.readAllBytes(Paths.get(pathToJsonRequests + "/test_3.json")));

	    this.mockMvc.perform(post("/order").content(jsonContent).contentType(MediaType.APPLICATION_JSON_VALUE))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("{\"totalCost\":9.60}")));
	}
	
	@Test
	@DisplayName("A order of each taco with two quantity each should equal 19.20 (Discount applied) ")
	void orderControllerPostOrderTest4() throws Exception {
		
		
	    String jsonContent = new String(Files.readAllBytes(Paths.get(pathToJsonRequests + "/test_4.json")));

	    this.mockMvc.perform(post("/order").content(jsonContent).contentType(MediaType.APPLICATION_JSON_VALUE))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("{\"totalCost\":19.20}")));
	}
	
	@Test
	@DisplayName("A veggie taco order with a quantity of 0, should throw a 400 BAD_REQUEST")
	void orderControllerPostOrderTest5() throws Exception {
		
	    String jsonContent = new String(Files.readAllBytes(Paths.get(pathToJsonRequests + "/test_5.json")));

	    this.mockMvc.perform(post("/order").content(jsonContent).contentType(MediaType.APPLICATION_JSON_VALUE))
		.andDo(print())
		.andExpect(status().isBadRequest())
		.andExpect(status().reason("The quantity of an order can't be less than or equal 0."))
		.andExpect(content().string(containsString("")));
	}
	
	@Test
	@DisplayName("A JSON with an quantity ordered of 101 should throw a 400 BAD_REQUEST and error message should state : 'The quantity of a single item order can't exceed 100.'")
	void orderControllerPostOrderTest11() throws Exception {
		
	    String jsonContent = new String(Files.readAllBytes(Paths.get(pathToJsonRequests + "/test_11.json")));

	    this.mockMvc.perform(post("/order").content(jsonContent).contentType(MediaType.APPLICATION_JSON_VALUE))
		.andDo(print())
		.andExpect(status().isBadRequest())
		.andExpect(status().reason("The quantity of a single item order can't exceed 100."))
		.andExpect(content().string(containsString("")));
	}
	
	@Test
	@DisplayName("An order with a id of 7 is not a valid Id for a menuItem and should throw a 400 BAD_REQUEST")
	void orderControllerPostOrderTest6() throws Exception {
		
	    String jsonContent = new String(Files.readAllBytes(Paths.get(pathToJsonRequests + "/test_6.json")));

	    this.mockMvc.perform(post("/order").content(jsonContent).contentType(MediaType.APPLICATION_JSON_VALUE))
		.andDo(print())
		.andExpect(status().isBadRequest())
		.andExpect(status().reason("The given id of 7 is not a valid id for a menu item."))
		.andExpect(content().string(containsString("")));
	}
	
	@Test
	@DisplayName("A JSON with an empty array should throw a 400 BAD_REQUEST and error message should state : 'The list of item orders can't be null or empty.'")
	void orderControllerPostOrderTest7() throws Exception {
		
	    String jsonContent = new String(Files.readAllBytes(Paths.get(pathToJsonRequests + "/test_7.json")));

	    this.mockMvc.perform(post("/order").content(jsonContent).contentType(MediaType.APPLICATION_JSON_VALUE))
		.andDo(print())
		.andExpect(status().isBadRequest())
		.andExpect(status().reason("The list of item orders can't be null or empty."))
		.andExpect(content().string(containsString("")));
	}
	
	
	//If an ItemOrder given in a JSON array as a empty object, the system will go in order from 1: id 2:quantity specifying the user the fields missing.
	@Test
	@DisplayName("A JSON with an array containing an empty object should throw a 400 BAD_REQUEST and error message should state : 'Item ordered is missing an Id.'")
	void orderControllerPostOrderTest8() throws Exception {
		
	    String jsonContent = new String(Files.readAllBytes(Paths.get(pathToJsonRequests + "/test_8.json")));

	    this.mockMvc.perform(post("/order").content(jsonContent).contentType(MediaType.APPLICATION_JSON_VALUE))
		.andDo(print())
		.andExpect(status().isBadRequest())
		.andExpect(status().reason("Item ordered is missing an Id."))
		.andExpect(content().string(containsString("")));
	}
	
	@Test
	@DisplayName("A JSON with an order missing an id should throw a 400 BAD_REQUEST and error message should state : 'Item ordered is missing an Id.'")
	void orderControllerPostOrderTest9() throws Exception {
		
	    String jsonContent = new String(Files.readAllBytes(Paths.get(pathToJsonRequests + "/test_9.json")));

	    this.mockMvc.perform(post("/order").content(jsonContent).contentType(MediaType.APPLICATION_JSON_VALUE))
		.andDo(print())
		.andExpect(status().isBadRequest())
		.andExpect(status().reason("Item ordered is missing an Id."))
		.andExpect(content().string(containsString("")));
	}
	
	@Test
	@DisplayName("A JSON with an order missing a quantity should throw a 400 BAD_REQUEST and error message should state : 'Item ordered is missing a quantity.'")
	void orderControllerPostOrderTest10() throws Exception {
		
	    String jsonContent = new String(Files.readAllBytes(Paths.get(pathToJsonRequests + "/test_10.json")));

	    this.mockMvc.perform(post("/order").content(jsonContent).contentType(MediaType.APPLICATION_JSON_VALUE))
		.andDo(print())
		.andExpect(status().isBadRequest())
		.andExpect(status().reason("Item ordered is missing a quantity."))
		.andExpect(content().string(containsString("")));
	}
	
	
	
	
}
