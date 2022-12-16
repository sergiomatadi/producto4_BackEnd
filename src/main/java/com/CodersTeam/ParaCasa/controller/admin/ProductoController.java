package com.CodersTeam.ParaCasa.controller.admin;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.CodersTeam.ParaCasa.entity.Producto;
import com.CodersTeam.ParaCasa.entity.Tipo;
import com.CodersTeam.ParaCasa.entity.Usuario;
import com.CodersTeam.ParaCasa.repository.ProductoRepository;
import com.CodersTeam.ParaCasa.repository.TipoRepository;


@Controller
public class ProductoController {
	
	@Autowired
	ProductoRepository proRep;
	
	@Autowired
	TipoRepository tipoRep;
	
	@GetMapping("/admin/productos")
	
	public ModelAndView urlProductos() {
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
				
				Iterable<Producto> productos = proRep.findAll();
				Iterable<Tipo> tipos=tipoRep.findAll();
				
				mv=new ModelAndView();
				mv.setViewName("/views/productos/listarProductos.html");
				mv.addObject("productos",productos);
				mv.addObject("tipos",tipos);		
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
	
	@GetMapping("/admin/productos/crearProducto")
	public ModelAndView crearMenu() {
		
		ModelAndView mv=new ModelAndView("/views/productos/crearProducto.html");
		
		Iterable<Tipo> tiposDisponibles=tipoRep.findAll();
	
		mv.addObject("tiposDisponibles",tiposDisponibles);	
		
		return mv;
	
	}
	
	@PostMapping("/admin/agregarProducto")
	public ModelAndView crearProducto(@RequestParam("descripcion")String descripcion,@RequestParam("kcal")int kcal,@RequestParam("tipo") long idTipo) {
		
		Optional<Tipo> ot=tipoRep.findById(idTipo);
		Tipo t=ot.get();
		
		Producto p=new Producto(null, descripcion, kcal, t);
		proRep.save(p);
		
		
		return urlProductos();
	}
	
	@GetMapping("/admin/editarProducto")
	public ModelAndView editarTipo(@RequestParam("id") long id) {
		ModelAndView mv=new ModelAndView("/views/productos/editarProducto");
		
		
		Optional<Producto> op=proRep.findById(id);		
		Producto p=op.get();
		Iterable<Tipo> tipos=tipoRep.findAll();
		
		
		mv.addObject("producto",p);
		mv.addObject("tipos",tipos);
		
		
		return mv;
	}
	
	@PostMapping("/admin/guardarProducto")
	public ModelAndView guardarProducto(@RequestParam("id")long id, @RequestParam("descripcion")String descripcion,@RequestParam("kcal")int kcal,@RequestParam("tipo")long idTipo) {
		Optional<Producto> op=proRep.findById(id);
		Optional<Tipo> ot=tipoRep.findById(idTipo);
		Producto p=op.get();
		Tipo t=ot.get();
		p.setDescripcion(descripcion);
		p.setKcal(kcal);
		p.setTipo(t);
		
		proRep.save(p);
		
		return urlProductos();
	}
	
	@PostMapping("/admin/eliminarProducto")
	public ModelAndView eliminarProducto(@RequestParam("id")long id) {
		proRep.deleteById(id);
		
		
		return urlProductos();
	}
	
	
}
