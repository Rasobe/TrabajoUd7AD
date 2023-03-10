package com.dam2.ud7.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder()).dataSource(dataSource)
				.usersByUsernameQuery("select username, password, enabled from usuarios where username = ?")
				.authoritiesByUsernameQuery("select username, role from usuarios where username = ?");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http.authorizeRequests()
	        .antMatchers("/login", "/register", "/register/guardar").permitAll()
	        .antMatchers("/usuarios/editar/miperfil", "/usuarios/editar/guardar").hasAnyRole("USER", "ADMIN")
	        .antMatchers("/usuarios/**").hasRole("ADMIN") // Restringe el acceso a todas las URLs relacionadas con /usuarios
	        .anyRequest().authenticated()
	        .and()
	    .formLogin()
	        .loginPage("/login").permitAll()
	        .and()
	    .logout()
	        .logoutUrl("/logout")
	        .logoutSuccessUrl("/login?logout")
	        .invalidateHttpSession(true)
	        .deleteCookies("JSESSIONID").permitAll();
	}
	
}
