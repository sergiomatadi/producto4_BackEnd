package com.CodersTeam.ParaCasa.controller.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.CodersTeam.ParaCasa.entity.Producto;
import com.CodersTeam.ParaCasa.entity.Tipo;
import com.CodersTeam.ParaCasa.entity.Usuario;
import com.CodersTeam.ParaCasa.repository.TipoRepository;
import com.CodersTeam.ParaCasa.repository.UsuarioRepository;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class TipoControllerApi {
	
	@Autowired
	private UsuarioRepository usuRep;
	@Autowired
	private TipoRepository tipoRep;
	
	private long getTipoUsuario(String authorization) {
		int i=authorization.indexOf("Bearer ");
		
		if(i>=0) {
	 		String token=authorization.substring(i+7).trim();
	 		
	 		
			Optional<Usuario> ouser = usuRep.findByToken(token);
			
			
			if(ouser.isPresent()) {
				Usuario u=ouser.get();
				return u.getRole().getId();
			}
			else {
				return -1;
			}
		}
		else {
			return -1;
		}
	}
	
	@Operation(summary = "Listar todos los tipos")
	@GetMapping("/api/tipos")
	public Collection<Tipo> listarTodosTipos(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, HttpServletResponse response) throws IOException{
		int i=authorization.indexOf("Bearer ");
		
		if(i>=0) {
	 		String token=authorization.substring(i+7).trim();
	 		
	 		
			Optional<Usuario> ouser = usuRep.findByToken(token);
			
			
			if(ouser.isPresent()) {
				Iterable<Tipo> it = tipoRep.findAll();
				Collection<Tipo> listado = new ArrayList<>();
				for (Tipo t : it) {
					listado.add(t);
				}
				return listado;
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
	
	@Operation(summary = "Insertar un tipo")
	@PostMapping("/api/tipos/insertar")
	public String insertar(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,@RequestBody Tipo tipo,HttpServletResponse response) throws IOException {
		return modificar(authorization,tipo,response);
	}
	
	@Operation(summary = "Modificar un tipo")
	@PutMapping("api/tipos/modificar")
	public String modificar(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,@RequestBody Tipo tipo,HttpServletResponse response) throws IOException {
		if(getTipoUsuario(authorization)==0) {
			tipoRep.save(tipo);
			return "OK";
		}
		else{
			response.sendError(401,"No esta autorizado para acceder a este recurso.");
			return null;
		}
	}
	
	@Operation(summary = "Buscar un tipo por ID")
	@GetMapping("/api/tipos/{id}")
	public Tipo leer(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,@PathVariable(name="id") Long id,HttpServletResponse response) throws IOException {
		if(getTipoUsuario(authorization)>=0) {
			Optional<Tipo> ot= tipoRep.findById(id);
			Tipo o=null;
			if(ot.isPresent()) {
				o=ot.get();
			}
			return o;		
		}
		else{
			response.sendError(401,"No esta autorizado para acceder a este recurso.");
			return null;
		}
	}
	@Operation(summary = "Eliminar un tipo")
	@DeleteMapping("/api/tipos/eliminar/{id}")
	public String eliminar(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,@PathVariable(name="id") Long id,HttpServletResponse response) throws IOException {
		if(getTipoUsuario(authorization)== 0) {
			tipoRep.deleteById(id);
			return "OK";
		}
		else {
			response.sendError(401,"No esta autorizado para acceder a este recurso.");
			return null;
		}
	}
	
	

}
