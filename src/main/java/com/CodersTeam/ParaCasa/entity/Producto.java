package com.CodersTeam.ParaCasa.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Producto implements Serializable{
	
	/**
	 * para el interface Serializable
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="Id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String descripcion;
	private int kcal;
	
	@ManyToOne(fetch=FetchType.EAGER,optional=false)
	@JoinColumn(name="id_tipo",nullable=false)
	private Tipo tipo;
	
	
	
	public Producto() {
		
	}

	public Producto(Long id, String descripcion, int kcal, Tipo tipo) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.kcal = kcal;
		this.tipo = tipo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getKcal() {
		return kcal;
	}

	public void setKcal(int kcal) {
		this.kcal = kcal;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	
	
	

}
