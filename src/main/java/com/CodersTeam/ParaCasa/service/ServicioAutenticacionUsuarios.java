package com.CodersTeam.ParaCasa.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.CodersTeam.ParaCasa.entity.Role;
import com.CodersTeam.ParaCasa.entity.Usuario;
import com.CodersTeam.ParaCasa.repository.UsuarioRepository;

public class ServicioAutenticacionUsuarios implements UserDetailsService {
	
	public static class DetallesUsuario implements UserDetails{
		
		private Usuario u;
		
		public DetallesUsuario(Usuario u) {
			this.u=u;
		}
		
		public Usuario getUsuario() {
			return u;
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			Collection<Role> l=new ArrayList<>();
			l.add(u.getRole());
			return l;
		}

		@Override
		public String getPassword() {
			if(u==null) {
				return null;
			}
			return u.getPassword();
		}

		@Override
		public String getUsername() {
			return u.getUsuario();
		}

		@Override
		public boolean isAccountNonExpired() {
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}

		@Override
		public boolean isEnabled() {
			return true;
		}
		
	}
	
	@Autowired
	UsuarioRepository usuRep;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario u=usuRep.findByUsuario(username);
		Role r=u.getRole();
		DetallesUsuario d=new DetallesUsuario(u);
		return d;
	}

}
