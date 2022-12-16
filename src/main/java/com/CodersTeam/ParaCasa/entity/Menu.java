package com.CodersTeam.ParaCasa.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Menu implements Serializable {
	
	
	/**
	 * para el interface Serializable
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Double precio;
	private String nombre;
	
	
	
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="menu_producto",joinColumns=@JoinColumn(name="id_menu"), inverseJoinColumns=@JoinColumn(name="id_producto"))
	private List<Producto> productos=new ArrayList<>();
	
	
	public Menu() {
		
	}

	public Menu(Long id, Double precio, String nombre) {
		super();
		this.id = id;
		this.precio = precio;
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public List<Producto> getProductos() {
		return productos;
	}
	
	


}
