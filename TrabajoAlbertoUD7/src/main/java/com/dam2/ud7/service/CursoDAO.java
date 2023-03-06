package com.dam2.ud7.service;

import java.util.List;

import com.dam2.ud7.models.Curso;

public interface CursoDAO {

	public List<Curso> findAll();

	public List<Curso> findAllByUserId(String username);

	public void save(Curso curso);
	
	public Curso findOne(Long id);
	
	public void delete(Long id);
	
	

	
}
