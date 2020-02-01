package com.domgarr.tacoloco;

import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.domgarr.tacoloco.model.MenuItem;

@SpringBootTest
class TacoLocoApplicationTests {

	public static HashMap<String, MenuItem> menuItems;
	public static final String VEGGIE_TACO = "Veggie Taco";
	public static final String CHICKEN_TACO = "Chicken Taco";
	public static final String BEEF_TACO = "Beef Taco";
	public static final String CHORIZO_TACO = "Chorizo Taco";

	
	
	
	static {
		MenuItem veggieTaco = new MenuItem(VEGGIE_TACO, 2.50);
		MenuItem chickenTaco = new MenuItem(CHICKEN_TACO, 3);
		MenuItem beefTaco = new MenuItem(BEEF_TACO, 3);
		MenuItem chorizoTaco = new MenuItem(CHORIZO_TACO, 3.50);
		
		menuItems = new HashMap<>();
		menuItems.put(VEGGIE_TACO, veggieTaco);
		menuItems.put(CHICKEN_TACO, chickenTaco);
		menuItems.put(BEEF_TACO, beefTaco);
		menuItems.put(CHORIZO_TACO, chorizoTaco);

	}
	
	@Test
	void contextLoads() {
	}

}
