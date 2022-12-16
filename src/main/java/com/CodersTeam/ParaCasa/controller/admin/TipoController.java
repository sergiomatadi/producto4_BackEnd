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

import com.CodersTeam.ParaCasa.entity.Menu;
import com.CodersTeam.ParaCasa.entity.Producto;
import com.CodersTeam.ParaCasa.entity.Tipo;
import com.CodersTeam.ParaCasa.entity.Usuario;
import com.CodersTeam.ParaCasa.repository.TipoRepository;


@Controller
public class TipoController {

	@Autowired
	TipoRepository tipoRep;
	
	@GetMapping("/admin/tipos")
	
	public ModelAndView urlTipos() {
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
				Iterable<Tipo> tipos = tipoRep.findAll();
				
				
				mv=new ModelAndView();
				mv.setViewName("/views/tipos/listarTipos.html");
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
	
	@GetMapping("/admin/tipos/crearTipo")
	public ModelAndView crearMenu() {
		
		ModelAndView mv=new ModelAndView();
		mv.setViewName("/views/tipos/crearTipo.html");
		return mv;
	
	}
	
	@PostMapping("/admin/agregarTipo")
	public ModelAndView agregarTipo(@RequestParam("nombre") String nombre) {
		
		
		Tipo t = new Tipo(0L,nombre);
		t=tipoRep.save(t);
	
			
		return urlTipos();
	}
	
	@GetMapping("/admin/editarTipo")
	public ModelAndView editarTipo(@RequestParam("id") long id) {
		ModelAndView mv=new ModelAndView("/views/tipos/editarTipo");
		
		
		Optional<Tipo> ot=tipoRep.findById(id);		
		Tipo t=ot.get();
		Iterable<Tipo> EditarTipo=tipoRep.findAll();
		
		
		mv.addObject("tipo",t);
		mv.addObject("EditarTipo",EditarTipo);
		
		
		return mv;
	}
	
	@PostMapping("/admin/modificarTipo")
	public ModelAndView modificarTipo(@RequestParam("id")long id,@RequestParam("nombre")String nombre) {
		Optional<Tipo> ot=tipoRep.findById(id);
		Tipo t=ot.get();
		
		t.setNombre(nombre);
		tipoRep.save(t);
		
		return urlTipos();
	}
	
	
	@PostMapping("/admin/eliminarTipo")
	public ModelAndView eliminarTipo(@RequestParam("id")long id) {
		tipoRep.deleteById(id);
		return urlTipos();
	}
	
}