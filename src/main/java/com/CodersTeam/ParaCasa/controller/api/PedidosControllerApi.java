package com.CodersTeam.ParaCasa.controller.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

import com.CodersTeam.ParaCasa.entity.Pedido;
import com.CodersTeam.ParaCasa.entity.Producto;
import com.CodersTeam.ParaCasa.repository.PedidoRepository;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class PedidosControllerApi {
	
	@Autowired
	PedidoRepository repPedido;
	
	@Operation(summary = "Listar todos los pedidos")
	@GetMapping("/api/pedidos")
	public Collection<Pedido>listarPedidos(){
		Collection<Pedido> l=new ArrayList<>();
		for(Pedido p: repPedido.findAll()) {
			l.add(p);
		}
		return l;
	}
	
	@Operation(summary = "Insertar un pedido")
	@PostMapping("/api/pedidos/insertar")
	public String insertar(@RequestBody Pedido pedido) {
		return modificar(pedido);
	}
	
	@Operation(summary = "Modificar un pedido")
	@PutMapping("api/pedidos/modificar")
	public String modificar(@RequestBody Pedido pedido) {
		
			repPedido.save(pedido);
			return "OK";
	}
	
	@Operation(summary = "Buscar un pedido por ID")
	@GetMapping("/api/pedidos/{id}")
	public Pedido leer(@PathVariable(name="id") Long id) {
		
			Optional<Pedido> op= repPedido.findById(id);
			Pedido o=null;
			if(op.isPresent()) {
				o=op.get();
			}
			return o;		
		
	}
	@Operation(summary = "Eliminarr un pedido")
	@DeleteMapping("/api/pedidos/eliminar/{id}")
	public String eliminar(@PathVariable(name="id") Long id) {
		
			repPedido.deleteById(id);
			return "OK";		
	}
}
