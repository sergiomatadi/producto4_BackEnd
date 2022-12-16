package com.CodersTeam.ParaCasa.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Tipo implements Serializable{
	
	/**
	 * para el interface Serializable
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="Id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String nombre;
	
	/*
	@OneToMany(
			mappedBy="tipo",
			fetch=FetchType.LAZY,
			cascade=CascadeType.ALL
	)
	private Set<Producto> productos;
	*/
	
	public Tipo() {
		
	}

	public Tipo(Long id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}
	
	/*
	public Set<Producto> getProductos(){
		return productos;
	}
	*/

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

}

