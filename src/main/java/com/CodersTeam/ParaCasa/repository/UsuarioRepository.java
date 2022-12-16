package com.CodersTeam.ParaCasa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.CodersTeam.ParaCasa.entity.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
	
	/*
	Usuario findById(long id);
	
	Iterable<Usuario> findAll();
	 */
	
	@Query(value="select * from usuario where usuario=?",nativeQuery=true)
	Usuario findByUsuario(String usuario);
	
	@Query(value="select * from usuario where token=?",nativeQuery=true)
	Optional<Usuario> findByToken(String token);
	
}
