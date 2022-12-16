package com.CodersTeam.ParaCasa.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Random;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="usuario")
public class Usuario implements Serializable{
	
	/**
	 * para el interface Serializable
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="Id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String nombre;
	
	@Column(name="DNI")
	private String dni;
	private String usuario;
	private String password;
	private String correo;
	private String token;
	
	
	@ManyToOne(fetch=FetchType.EAGER,optional=false)
	@JoinColumn(name="id_rol",nullable=false)
	private Role role;
	
	@JsonIgnore
	@OneToMany(
			mappedBy="usuario",
			fetch=FetchType.EAGER,
			cascade=CascadeType.ALL
	)
	private Set<Pedido> pedidos;
	
	public Usuario() {
		
	}
	
	public Usuario(Long id, String nombre, String dni, String usuario, String password, String correo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.dni = dni;
		this.usuario = usuario;
		this.password = password;
		this.correo = correo;
	}

	public Set<Pedido> getPedidos(){
		return pedidos;
		
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
	public String getDNI() {
		return dni;
	}
	public void setDNI(String dNI) {
		dni = dNI;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}