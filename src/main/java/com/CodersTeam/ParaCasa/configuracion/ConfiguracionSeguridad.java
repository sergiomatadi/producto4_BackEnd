package com.CodersTeam.ParaCasa.configuracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class ConfiguracionSeguridad extends WebSecurityConfigurerAdapter{
	
	@Autowired
	UserDetailsService servicioAutenticacion;
	
	@Override
	  protected void configure(HttpSecurity httpSecurity) throws Exception {
		  httpSecurity
		    .csrf()
		    .disable()
		    .authorizeRequests()
		    .antMatchers(
		    		"/login",
		    		"/registro",
		    		"/realizarRegistro",
		    		"/",
		    		"/images/50513889-icono-negro-de-diseño-vector-de-la-casa-hogar-símbolo-removebg-preview.png",
		    		"/api/menus",
		    		"/api/pedidos",
		    		"/api/usuarios",
		    		"/api/user",
		    		"/api/productos",
		    		"/api/tipos"
		    		)
		    .permitAll()
		    .anyRequest()
		    .authenticated()
		    .and()
		    .formLogin()
		    .loginPage("/login");
	  }
	
	
	@Bean
	  public AuthenticationProvider daoAuthenticationProvider() {
	    DaoAuthenticationProvider provider = 
	      new DaoAuthenticationProvider();
	    provider.setPasswordEncoder(passwordEncoder());
	    provider.setUserDetailsService(servicioAutenticacion);
	    return provider;
	  }
	  
	  @Bean
	  public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	  }

}
