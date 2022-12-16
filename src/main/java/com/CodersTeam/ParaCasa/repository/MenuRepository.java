package com.CodersTeam.ParaCasa.repository;

import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.CodersTeam.ParaCasa.entity.Menu;

public interface MenuRepository extends CrudRepository<Menu, Long> {
	
	
	
	
	@Modifying
	@Transactional
	@Query(value="insert into menu_producto (id_producto,id_menu) values (?,?)",nativeQuery=true)
	void insertarProductoEnMenu(long id_producto,long id_menu);
	
	
	@Modifying
	@Transactional
	@Query(value="delete from menu_producto where id_producto=? and id_menu=?",nativeQuery=true)
	void eliminarProductoDelMenu(long id_producto,long id_menu);


	@Query(value="select m.* "
			+ "from menu m "
			+ "     join menu_pedido mp on(m.id=mp.id_menu) "
			+ "     join pedido p on (mp.id_pedido=p.id) "
			+ "where p.estado=0 and p.id_usuario=?"
			,nativeQuery=true)
	Collection<Menu> getMenusEnPedidoAbierto(Long idUsuario);
	
	/*Eliminar los menus de los pedidos */
	
	@Modifying
	@Transactional
	@Query(value="delete from menu_pedido where id_menu=? and id_pedido=?",nativeQuery=true)
	void eliminarMenuDelPedido(long id_menu,long id_pedido);
}
