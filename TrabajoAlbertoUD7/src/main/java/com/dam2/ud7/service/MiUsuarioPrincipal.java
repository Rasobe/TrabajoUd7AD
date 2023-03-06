package com.dam2.ud7.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.management.relation.Role;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.dam2.ud7.entity.Usuario;

public class MiUsuarioPrincipal implements UserDetails {

	private static final long serialVersionUID = 1L;
	private Usuario usuario;

	public MiUsuarioPrincipal(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		Set<Role> roles = usuario.getRoles();
		ArrayList<GrantedAuthority> authorities = new ArrayList<>();
		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return usuario.getPassword();
	}

	@Override
	public String getUsername() {
		return usuario.getUsername();
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