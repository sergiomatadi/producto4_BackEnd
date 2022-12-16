package com.CodersTeam.ParaCasa.repository;

import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.CodersTeam.ParaCasa.entity.Menu;
import com.CodersTeam.ParaCasa.entity.Pedido;

public interface PedidoRepository extends CrudRepository<Pedido, Long> {
	/*
	Pedido findById(long id);

	
	Iterable<Pedido> findAll();
	*/
	
	@Modifying
	@Transactional
	@Query(value="insert into menu_pedido (id_menu,id_pedido) values (?,?)",nativeQuery=true)
	void insertarMenuEnPedido(long id_menu,long id_pedido);
	
	
	@Query(value="select p.* from pedido p where p.estado=0 and p.id_usuario=?",nativeQuery=true)
	Optional<Pedido> findAbiertoUsuario(Long idUsuario);
	
	@Modifying
	@Transactional
	@Query(value="delete from menu_pedido where id_menu=? and id_pedido=?",nativeQuery=true)
	void eliminarPedido(long id_menu,long id_pedido);
	
	
}
