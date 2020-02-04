package com.domgarr.tacoloco.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.domgarr.tacoloco.model.MenuItem;
import com.domgarr.tacoloco.service.MenuService;

@RestController
public class MenuController {
	@Autowired
	private MenuService menuService;
	
	@RequestMapping(value="/menu", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ArrayList<MenuItem> getMenu() {
		return menuService.getMenuAsList();
	}
}
