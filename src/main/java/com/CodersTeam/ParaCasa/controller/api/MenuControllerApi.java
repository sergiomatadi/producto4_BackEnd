package com.CodersTeam.ParaCasa.controller.api;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CodersTeam.ParaCasa.entity.Menu;
import com.CodersTeam.ParaCasa.repository.MenuRepository;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class MenuControllerApi {
	
	@Autowired
	MenuRepository repMenu;
	
	@Operation(summary = "Listar todos los menus")
	@GetMapping("/api/menus")
	public Collection<Menu> listarMenus(){
		Collection<Menu> l=new ArrayList<>();
		for( Menu m : repMenu.findAll()) {
			l.add(m);
		}
		
		return  l;
	}

}
 	