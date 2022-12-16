package com.CodersTeam.ParaCasa.controller.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.CodersTeam.ParaCasa.controller.UtilidadesAutenticacion;
import com.CodersTeam.ParaCasa.entity.Menu;
import com.CodersTeam.ParaCasa.entity.Pedido;
import com.CodersTeam.ParaCasa.entity.Usuario;
import com.CodersTeam.ParaCasa.repository.MenuRepository;
import com.CodersTeam.ParaCasa.repository.PedidoRepository;
import com.CodersTeam.ParaCasa.repository.UsuarioRepository;

@Controller
public class anyadirMenu {
	
	@Autowired
	UsuarioRepository usuRep;
	
	@Autowired
	PedidoRepository pedidoRep; 
	
	@Autowired
	MenuRepository menuRep;
	
	@GetMapping("/anyadirMenu")
	public ModelAndView anyadirMenu(@RequestParam(name="idMenu") Long idMenu) {
		ModelAndView mv=null;
		
		Usuario u=UtilidadesAutenticacion.getUsuario();
		if(u!=null) {
			Pedido pedidoAbierto=null;
			Set<Pedido> pedidos=u.getPedidos();
			for(Pedido p : pedidos) {
				if(p.getEstado()==Pedido.EDITANDO) {
					pedidoAbierto=p;
				}
			}
			if(pedidoAbierto==null) {
				pedidoAbierto=new Pedido(null,LocalDateTime.now(),u,Pedido.EDITANDO);
				pedidoRep.save(pedidoAbierto);
			}
			pedidoRep.insertarMenuEnPedido(idMenu, pedidoAbierto.getId());
			mv=new ModelAndView("/views/pedidos/exitoPedido.html");
			
		}
		
		
		return mv;
	}
	
	@GetMapping("/pedidoUsuario")
	public ModelAndView pedidoUsuario() {
		ModelAndView mv=null;
		Usuario u=UtilidadesAutenticacion.getUsuario();
		if(u!=null) {
			Collection<Menu> menues=menuRep.getMenusEnPedidoAbierto(u.getId()); 
			Optional<Pedido> op=pedidoRep.findAbiertoUsuario(u.getId());
			if(op.isPresent()) {
				Pedido p=op.get();
				mv=new ModelAndView("/views/pedidos/carritoPedido.html");
				mv.addObject("pedido",p);
				mv.addObject("menues",menues);
			}else {
				mv=new ModelAndView("/views/pedidos/carritoVacio.html");
			}
			
		}
		return mv;
	} 
	
	@GetMapping("/confirmarPedido")
	public ModelAndView confirmarPedido(@RequestParam(name="idPedido") Long idPedido) {
		ModelAndView mv=null;
		
		Optional<Pedido> op=pedidoRep.findById(idPedido);
		if(op.isPresent()) {
			Pedido p=op.get();
			p.setEstado(Pedido.ENVIADO);
			pedidoRep.save(p);
			mv=new ModelAndView("/views/pedidos/pedidoEnviado.html");
		}
		
		return mv;
	}
	
	@PostMapping("/eliminarPedidoUsuario")
	public ModelAndView eliminarPedidoUsuario(@RequestParam("id_menu")long id_menu,@RequestParam("id_pedido")long id_pedido) {
		// usar el repositorioo para recuperar el pedido 
		menuRep.eliminarMenuDelPedido(id_menu, id_pedido);
		// y eliminarlo 
		
		return pedidoUsuario();
	}
	
	@GetMapping("/verToken")
	public ModelAndView verToken() {
		ModelAndView mv=null;
		Usuario u=UtilidadesAutenticacion.getUsuario();
		if(u!=null) {
			mv=new ModelAndView("/views/usuarios/verToken.html");
			mv.addObject("token",u.getToken());
		}
		return mv;
	}
	
}
