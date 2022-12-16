package com.CodersTeam.ParaCasa.service;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

@EnableWebSecurity
public class ServicioSeguridad {

	@Bean 
	public UserDetailsService userDetailsService() {
		
		return new ServicioAutenticacionUsuarios();
		
	}
	
	
}
