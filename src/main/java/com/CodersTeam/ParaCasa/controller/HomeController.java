package com.CodersTeam.ParaCasa.controller;

import java.util.AbstractCollection;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.CodersTeam.ParaCasa.entity.Menu;

import com.CodersTeam.ParaCasa.repository.MenuRepository;



@Controller
public class HomeController {
	
	@Autowired
	MenuRepository repositorioMenu;
	
	
	
	
	@GetMapping("/")
	public ModelAndView home() {
		
		ModelAndView mv=null;
		if(UtilidadesAutenticacion.estaAutenticado()) {
			
			if(UtilidadesAutenticacion.esAdmin()) {
				 mv=new ModelAndView("homeAdmin");
			}
			else {
				 mv=new ModelAndView("home");
			}
		    mv.addObject("esAdmin",UtilidadesAutenticacion.esAdmin());
			mv.addObject("nombreUsuario",UtilidadesAutenticacion.getNombreUsuario());
		}
		else {
			mv=new ModelAndView("views/login/login");
		}
		return mv;
	}
	
	
	@GetMapping("/menus/seleccionMenu")
	public ModelAndView SeleccionMenu() {
		ModelAndView mv;
		
		Iterable<Menu> menus= repositorioMenu.findAll();
		mv=new ModelAndView("/views/menus/seleccionMenu");
		mv.addObject("menus",menus);
		mv.addObject("nombreUsuario",UtilidadesAutenticacion.getNombreUsuario());
		return mv;
		
	}
	
	@GetMapping("/menus/menuBebida")
	public String SeleccionBebida() {
		return "/views/menus/menuBebida";
	}
	
	

}
