package com.CodersTeam.ParaCasa.controller.admin;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.CodersTeam.ParaCasa.entity.Menu;
import com.CodersTeam.ParaCasa.entity.Producto;
import com.CodersTeam.ParaCasa.entity.Usuario;
import com.CodersTeam.ParaCasa.repository.MenuRepository;
import com.CodersTeam.ParaCasa.repository.ProductoRepository;

@Controller
public class MenuController {
	
	@Autowired
	MenuRepository menuRep;
	
	@Autowired
	ProductoRepository productoRep;
	
	@GetMapping("/admin/menus")
	
	public ModelAndView urlMenus() {
		Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ModelAndView mv=null;
		if( principal instanceof UserDetails) {
			UserDetails ud=(UserDetails)principal;
		    Collection<? extends GrantedAuthority> roles=ud.getAuthorities();
		    boolean tieneRoleAdmin=false;
		    for(GrantedAuthority role : roles) {
		    	if(role.getAuthority().equalsIgnoreCase("admin")) {
		    		tieneRoleAdmin=true;
		    	}
		    }
		    
			if(tieneRoleAdmin) {
				Iterable<Menu> menus= menuRep.findAll();
				
				
				mv=new ModelAndView();
				mv.setViewName("/views/menus/listarMenus.html");
				mv.addObject("menus",menus);
			}
			else {
				mv=new ModelAndView("/error/403");
			}
		}
		else {
			mv=new ModelAndView("/error/403");
		}
		
		return mv;	
	}
	
	
	@GetMapping("/admin/menus/crearMenu")
	public ModelAndView crearMenu() {
		
		ModelAndView mv=new ModelAndView();
		mv.setViewName("/views/menus/crearMenu.html");
		return mv;
	
	}
	
	@PostMapping("/admin/agregarMenu")
	public ModelAndView agregarMenu(@RequestParam("nombre") String nombre,@RequestParam("precio") double precio) {
		
		Menu m=new Menu(0L,precio,nombre);
		m=menuRep.save(m);
		
		return editarProductosMenu(m.getId());
	}
	
	// edita al crear Menu
	@PostMapping("/admin/editarProductosMenu")
	public ModelAndView editarProductosMenu(@RequestParam("id") long id) {
		ModelAndView mv=new ModelAndView("/views/menus/editarProductoMenu.html");
		
		// Obtener el menu con ese id y la lista de productos disponibles para insertar
		Optional<Menu> om=menuRep.findById(id);		
		Menu m=om.get();
		Iterable<Producto> productosDisponibles=productoRep.listarDisponiblesParaMenu(id);
		
		// pasarle a la vista  el menu y los productos disponibles para insertar
		mv.addObject("menu",m);
		mv.addObject("productosDisponibles",productosDisponibles);
		
		// lanzar la vista.
		return mv;
	}
	
	// anyade Producto al crear menu
	@PostMapping("/admin/anyadirProductoMenu")
	public ModelAndView anyadirProductoMenu(@RequestParam("id_menu") long id_menu,@RequestParam("id_producto")long id_producto) {
		
		menuRep.insertarProductoEnMenu(id_producto, id_menu);
		
		return editarProductosMenu(id_menu);
		
	}
	
	// elimina Producto al crear menu
	@PostMapping("/admin/eliminarProductoMenu")
	public ModelAndView eliminarProductoMenu(@RequestParam("id_menu") long id_menu,@RequestParam("id_producto")long id_producto) {
		
		menuRep.eliminarProductoDelMenu(id_producto, id_menu);
		
		return editarProductosMenu(id_menu);
		
	}
	
	@GetMapping ("/admin/editarMenu")	
	public ModelAndView editarMenu(@RequestParam("id") long id) {
		
		ModelAndView mv=new ModelAndView("/views/menus/editarMenu");
		Optional<Menu> om=menuRep.findById(id);
		Menu m=om.get();
		Iterable<Menu> EditarMenu=menuRep.findAll();
		
		mv.addObject("menu", m);
		mv.addObject("EditarMenu", EditarMenu);
		
		return mv;
	}
	
	@PostMapping("/admin/modificarMenu")
	public ModelAndView modificarMenu(@RequestParam("id") long id, @RequestParam("precio") double precio, @RequestParam("nombre")String nombre) {
		Optional<Menu> om=menuRep.findById(id);
		Menu m=om.get();
		
		m.setNombre(nombre);
		m.setPrecio(precio);
		
		return urlMenus();		
	}
	
	@PostMapping("/admin/eliminarMenu")
	public ModelAndView eliminarMenu(@RequestParam("id")long id) {
		menuRep.deleteById(id);
		return urlMenus();
	}

}
