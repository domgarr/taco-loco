package com.domgarr.tacoloco;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.domgarr.tacoloco.model.MenuItem;
import com.domgarr.tacoloco.service.MenuService;

import static com.domgarr.tacoloco.service.MenuService.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class MenuServiceTests extends TacoLocoApplicationTests {

	@Autowired
	private MenuService menuService;
	
	private static HashMap<String, MenuItem> menuItems;
	
	@BeforeEach
	void init(){
		menuItems = menuService.getMenuMapWithStringAsKey();
	}
	
	/*
	 * Menu Items:
	 * 	Veggie Taco : 2.50
	 *  Chicken Taco : 3.00
	 *  Beef Taco : 3.00
	 *  Chorizo Taco : 3.50
	 * 
	 */
	
	//Verify the name of the menuItems stored in MenuService are spelled correctly as per document.
	@Test
	@DisplayName("VEGGIE_TACO should return 'Veggie Taco'")
	void menuItemNameTest1() {
		assertEquals("Veggie Taco", VEGGIE_TACO);
	}
	
	@Test
	@DisplayName("BEEF_TACO should return 'Beef Taco'")
	void menuItemNameTest2() {
		assertEquals("Beef Taco", BEEF_TACO );
	}
	
	@Test
	@DisplayName("CHICKEN_TACO should return 'Chicken Taco'")
	void menuItemNameTest3() {
		assertEquals("Chicken Taco", CHICKEN_TACO );
	}
	
	@Test
	@DisplayName("CHORIZO_TACO should return 'Chorizo Taco'")
	void menuItemNameTest4() {
		assertEquals("Chorizo Taco", CHORIZO_TACO);
	}
	
	//Verify the MenuItems are priced as per document.
	@Test
	@DisplayName("VEGGIE_TACO should return 2.50")
	void menuItemCostTest1() {
		assertEquals(2.50, menuItems.get(VEGGIE_TACO).getCost());
	}
	
	@Test
	@DisplayName("BEEF_TACO should return 3.00")
	void menuItemCostTest2() {
		assertEquals(3.00, menuItems.get(BEEF_TACO).getCost());
	}
	
	@Test
	@DisplayName("CHICKEN_TACO should return 3.00")
	void menuItemCostTest3() {
		assertEquals(3.00, menuItems.get(CHICKEN_TACO).getCost());
	}
	
	@Test
	@DisplayName("CHORIZO_TACO should return 3.50")
	void menuItemCostTest4() {
		assertEquals(3.50, menuItems.get(CHORIZO_TACO).getCost());
	}
	
	//Test the MenuMap with integer keys. I believe testing for an empty map and map size is correct size of four is sufficient.
	@Test
	@DisplayName("getMenuMapWithIntegerAsKey() should return a map with four keys.")
	void hashMapIntegerKeyTest() {
		HashMap<Integer, MenuItem> menuItems = menuService.getMenuMapWithIntegerAsKey();
		assertEquals(4, menuItems.size());
	}
	
}
