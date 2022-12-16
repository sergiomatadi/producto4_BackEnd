package com.CodersTeam.ParaCasa.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name="roles")
public class Role implements GrantedAuthority{
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long id;
	private String nombre;
	
	
	
	public Role(Long id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}
	
	public Role() {
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String getAuthority() {
		return nombre;
	}
	
	
	
	
}
