package com.domgarr.tacoloco.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.domgarr.tacoloco.model.MenuItem;
/**
 *	MenuService will act as a service and DAO since a database wasn't required.
 *
 * There are two HashMap implementations containing all the MenuItems on TacoLoco's menu.
 * 	-The HashMap (HM) with the String key is used in testing.
 * 	-The HM with the Integer key is used for mapping id's to their respective MenuItem.
 * @author Domenic
 *
 */

@Service
public class MenuService {
	public static final String VEGGIE_TACO = "Veggie Taco";
	public static final String CHICKEN_TACO = "Chicken Taco";
	public static final String BEEF_TACO = "Beef Taco";
	public static final String CHORIZO_TACO = "Chorizo Taco";
	
	public static final double VEGGIE_TACO_COST = 2.50;
	public static final double CHICKEN_TACO_COST = 3.00;
	public static final double BEEF_TACO_COST = 3.00;
	public static final double CHORIZO_TACO_COST = 3.50;

	private MenuItem veggieTaco;
	private MenuItem chickenTaco;
	private MenuItem beefTaco;
	private MenuItem chorizoTaco;
	
	public MenuService() {
		veggieTaco = new MenuItem(1, VEGGIE_TACO, VEGGIE_TACO_COST);
		chickenTaco = new MenuItem(2, CHICKEN_TACO, CHICKEN_TACO_COST);
		beefTaco = new MenuItem(3, BEEF_TACO, BEEF_TACO_COST);
		chorizoTaco = new MenuItem(4, CHORIZO_TACO, CHORIZO_TACO_COST);
	}
	
	public HashMap<String, MenuItem> getMenuMapWithStringAsKey(){
		HashMap<String, MenuItem> menuMap = new HashMap<>();
		menuMap.put(VEGGIE_TACO, veggieTaco);
		menuMap.put(CHICKEN_TACO, chickenTaco);
		menuMap.put(BEEF_TACO, beefTaco);
		menuMap.put(CHORIZO_TACO, chorizoTaco);
		
		return menuMap;
	}
	
	public HashMap<Integer, MenuItem> getMenuMapWithIntegerAsKey(){
		HashMap<Integer, MenuItem> menuMap = new HashMap<>();
		menuMap.put(veggieTaco.getId(), veggieTaco);
		menuMap.put(chickenTaco.getId(), chickenTaco);
		menuMap.put(beefTaco.getId(), beefTaco);
		menuMap.put(chorizoTaco.getId(), chorizoTaco);
		
		return menuMap;
	}
	
	public ArrayList<MenuItem> getMenuAsList(){
		ArrayList<MenuItem> menuItemList = new ArrayList<>();		
		menuItemList.add(veggieTaco);
		menuItemList.add(chickenTaco);
		menuItemList.add(beefTaco);
		menuItemList.add(chorizoTaco);
		return  menuItemList;
	}
}
