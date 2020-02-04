package com.domgarr.tacoloco;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
public class MenuControllerTests extends TacoLocoApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	
	private final String pathToJsonResponse = Paths.get("").toAbsolutePath() + "/src/test/java/com/domgarr/json_responses";
	
	@Test
	@DisplayName("/menu should respond with a JSON array containing four items.")
	void orderControllerPostOrderTest1() throws Exception {
	    String jsonContent = new String(Files.readAllBytes(Paths.get(pathToJsonResponse + "/test_1_response.json")));
	    //Remove all tabs, carriage-return and new-line from json string.
	    jsonContent = jsonContent.replaceAll("[\\t\\n\\r]+", "");
	    
	    
	    this.mockMvc.perform(get("/menu").contentType(MediaType.APPLICATION_JSON_VALUE))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().string(containsString(jsonContent)));
	}
	
}
