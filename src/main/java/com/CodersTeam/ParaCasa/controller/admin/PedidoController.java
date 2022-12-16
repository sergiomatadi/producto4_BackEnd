package com.CodersTeam.ParaCasa.controller.admin;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.CodersTeam.ParaCasa.entity.Menu;
import com.CodersTeam.ParaCasa.entity.Pedido;
import com.CodersTeam.ParaCasa.repository.MenuRepository;
import com.CodersTeam.ParaCasa.repository.PedidoRepository;


@Controller
public class PedidoController {
	
	@Autowired
	PedidoRepository pedRep;
	
	@Autowired
	MenuRepository menuRep;
	
	
	@GetMapping("/admin/pedidos")
	public ModelAndView urlPedidos() {
		Iterable<Pedido> pedidos= pedRep.findAll();
		
		
		ModelAndView mv=new ModelAndView();
		mv.setViewName("/views/pedidos/listarPedidos.html");
		mv.addObject("pedidos",pedidos);
		
		return mv;
	}
	
	@PostMapping("/admin/eliminarPedido")
	public ModelAndView eliminarPedido(@RequestParam("id")long id,@RequestParam("id_menu")long id_menu,@RequestParam("id_pedido")long id_pedido) {
		pedRep.eliminarPedido(id_pedido, id_menu);
		pedRep.deleteById(id);
		
		return urlPedidos();
	}
	

}
