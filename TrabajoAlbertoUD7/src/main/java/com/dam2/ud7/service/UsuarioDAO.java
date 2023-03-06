package com.dam2.ud7.service;

import java.util.List;

import com.dam2.ud7.models.Usuario;

public interface UsuarioDAO {

	public List<Usuario> findAll();

	public void save(Usuario cliente);
	
	public Usuario findOne(Long id);
	
	public void delete(Long id);

	
}
