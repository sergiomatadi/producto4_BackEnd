package com.CodersTeam.ParaCasa.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.CodersTeam.ParaCasa.entity.Tipo;
import com.CodersTeam.ParaCasa.entity.Usuario;

public interface TipoRepository extends CrudRepository<Tipo, Long> {
	
	@Query(value="select p.id, p.id_tipo from producto p left join tipo t on p.id = t.nombre",nativeQuery=true)
	Iterable<Tipo> tiposDisponibles(long id);
	
	
}
