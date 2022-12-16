package com.CodersTeam.ParaCasa.controller.api;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;


import com.CodersTeam.ParaCasa.entity.Usuario;
import com.CodersTeam.ParaCasa.repository.UsuarioRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;


@RestController
public class UsuarioControllerApi {
	
	@Autowired
	private UsuarioRepository usuRep; 
	
	
	
	@Operation(summary = "Listar todos los Usuarios")
	@GetMapping("/api/user")
	public Usuario user(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, HttpServletResponse response) throws IOException {
		int i=authorization.indexOf("Bearer ");
		if(i>=0) {
	 		String token=authorization.substring(i+7).trim();
	 		
	 		
			Optional<Usuario> ouser = usuRep.findByToken(token);
			
			if(ouser.isPresent()) {				
				return ouser.get();
			}
			else {
				response.sendError(401,"No esta autorizado para acceder a este recurso.");
				return null;
			}
		}
		else {
			response.sendError(401,"No esta autorizado para acceder a este recurso.");
			return null;
		}
	}

	
}
