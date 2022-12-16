package com.CodersTeam.ParaCasa.controller;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.CodersTeam.ParaCasa.entity.Usuario;
import com.CodersTeam.ParaCasa.service.ServicioAutenticacionUsuarios.DetallesUsuario;


public class UtilidadesAutenticacion {
	
	public static boolean esAdmin() {
		boolean tieneRoleAdmin=false;
		Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ModelAndView mv=null;
		if( principal!=null && principal instanceof UserDetails) {
			UserDetails ud=(UserDetails)principal;
		    Collection<? extends GrantedAuthority> roles=ud.getAuthorities();
		    
		    for(GrantedAuthority role : roles) {
		    	if(role.getAuthority().equalsIgnoreCase("admin")) {
		    		tieneRoleAdmin=true;
		    	}
		    }
		    
		}
		
		return tieneRoleAdmin;
	}
	
	public  static String getNombreUsuario() {
		String nombre="";
		boolean tieneRoleAdmin=false;
		Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ModelAndView mv=null;
		if( principal!=null && principal instanceof UserDetails) {
			UserDetails ud=(UserDetails)principal;
			nombre=ud.getUsername();
		}
		return nombre;
	}

	public static boolean estaAutenticado() {
		String nombre="";
		boolean tieneRoleAdmin=false;
		Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return principal instanceof UserDetails;
	}
	
	public static Usuario getUsuario() {
		Usuario u=null;
		Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal!=null && principal instanceof DetallesUsuario) {
			DetallesUsuario ud=(DetallesUsuario)principal;
			u=ud.getUsuario();
		}
		
		return u;
	}
	
}
