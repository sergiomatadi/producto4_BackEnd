package com.CodersTeam.ParaCasa.controller.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

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
import org.springframework.web.servlet.ModelAndView;

import com.CodersTeam.ParaCasa.entity.Producto;
import com.CodersTeam.ParaCasa.entity.Usuario;
import com.CodersTeam.ParaCasa.repository.ProductoRepository;
import com.CodersTeam.ParaCasa.repository.UsuarioRepository;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class ProductosControllerApi {
	
	@Autowired
	private UsuarioRepository usuRep;
	
	@Autowired 
	private ProductoRepository proRep;
	
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
	
	
	@Operation(summary = "Listar todos los productos")
	@GetMapping("/api/productos")
	public Collection<Producto> listarTodosProductos(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, HttpServletResponse response) throws IOException {
		if(getTipoUsuario(authorization)>=0) {
			// obtener del repositorio un List de Productos
			Iterable<Producto> it=proRep.findAll();
			Collection<Producto> listado=new ArrayList<>();
			for(Producto p : it) {
				listado.add(p);
			}
			return listado;
		}
		else {
			response.sendError(401,"No esta autorizado para acceder a este recurso.");
			return null;
		}
	}
	
	@Operation(summary = "Insertar un producto")
	@PostMapping("/api/productos/insertar")
	public String insertar(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,@RequestBody Producto producto,HttpServletResponse response) throws IOException {
		return modificar(authorization,producto,response);
	}
	
	@Operation(summary = "Modificar un producto")
	@PutMapping("api/productos/modificar")
	public String modificar(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,@RequestBody Producto producto,HttpServletResponse response) throws IOException {
		if(getTipoUsuario(authorization)==0) {
			proRep.save(producto);
			return "OK";
		}
		else{
			response.sendError(401,"No esta autorizado para acceder a este recurso.");
			return null;
		}
	}
	
	@Operation(summary = "Buscar un producto por ID")
	@GetMapping("/api/productos/{id}")
	public Producto leer(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,@PathVariable(name="id") Long id,HttpServletResponse response) throws IOException {
		if(getTipoUsuario(authorization)>=0) {
			Optional<Producto> op= proRep.findById(id);
			Producto o=null;
			if(op.isPresent()) {
				o=op.get();
			}
			return o;		
		}
		else{
			response.sendError(401,"No esta autorizado para acceder a este recurso.");
			return null;
		}
	}
	@Operation(summary = "Eliminar un producto")
	@DeleteMapping("/api/productos/eliminar/{id}")
	public String eliminar(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,@PathVariable(name="id") Long id,HttpServletResponse response) throws IOException {
		if(getTipoUsuario(authorization)== 0) {
			proRep.deleteById(id);
			return "OK";
		}
		else {
			response.sendError(401,"No esta autorizado para acceder a este recurso.");
			return null;
		}
	}
	

}
