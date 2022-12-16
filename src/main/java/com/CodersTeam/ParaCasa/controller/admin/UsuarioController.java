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

import com.CodersTeam.ParaCasa.entity.Role;
import com.CodersTeam.ParaCasa.entity.Usuario;
import com.CodersTeam.ParaCasa.repository.RoleRepository;
import com.CodersTeam.ParaCasa.repository.UsuarioRepository;

@Controller
public class UsuarioController {
	
	@Autowired
	UsuarioRepository usuarioRep;
	
	@Autowired
	RoleRepository rolRep;
	
	@GetMapping("/admin/usuarios")
	public ModelAndView urlUsuarios() {
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
				Iterable<Usuario> usuarios = usuarioRep.findAll();
				
				
				mv=new ModelAndView();
				mv.setViewName("/views/usuarios/listarUsuario.html");
				mv.addObject("usuarios",usuarios);
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
	
	@GetMapping ("/admin/editarUsuario")
	public ModelAndView editarUsuario(@RequestParam("id")long id) {
		ModelAndView mv=new ModelAndView("/views/usuarios/editarUsuario");
		
		Optional<Usuario> ou= usuarioRep.findById(id);
		Usuario u=ou.get();
		Iterable<Role> roles=rolRep.findAll();
		
		mv.addObject("usuario", u);
		mv.addObject("roles", roles);
		
		return mv;
	}
	
	@PostMapping("/admin/guardarUsuario")
	public ModelAndView guardarUsuario(@RequestParam("id")long id, @RequestParam("nombre")String nombre,@RequestParam("DNI")String DNI,@RequestParam("usuario")String usuario,@RequestParam("password")String password,@RequestParam("correo")String correo,@RequestParam("role")long idRole) {
		
		Optional<Usuario> ou=usuarioRep.findById(id);
		Optional<Role> or=rolRep.findById(idRole);
		Usuario u=ou.get();
		Role r=or.get();
		u.setCorreo(correo);
		u.setDNI(DNI);
		u.setNombre(nombre);
		u.setPassword(password);
		u.setUsuario(usuario);
		u.setRole(r);
		
		usuarioRep.save(u);
		
		return urlUsuarios();
		
	}
	
	@PostMapping("/admin/aliminarUsuario")
	public ModelAndView eliminarUsuario(@RequestParam("id")long id) {
		usuarioRep.deleteById(id);
		return urlUsuarios();
	}
	
	
	
}

