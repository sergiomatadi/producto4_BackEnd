package com.CodersTeam.ParaCasa.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Reserva {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private LocalDateTime tiempo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_usuario",nullable=false)
	private Usuario usuario;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="menu_reserva",joinColumns=@JoinColumn(name="id_reserva"),inverseJoinColumns=@JoinColumn(name="id_menu"))
	private List<Menu> menus=new ArrayList<>();
	
	public Reserva() {
		
	}

	public Reserva(Long id, LocalDateTime tiempo, Usuario usuario) {
		super();
		this.id = id;
		this.tiempo = tiempo;
		this.usuario = usuario;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getTiempo() {
		return tiempo;
	}

	public void setTiempo(LocalDateTime tiempo) {
		this.tiempo = tiempo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	

}

