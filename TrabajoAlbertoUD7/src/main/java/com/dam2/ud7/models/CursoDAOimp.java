package com.dam2.ud7.models;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dam2.ud7.entity.Curso;

@Repository
public class CursoDAOimp implements CursoDAO {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Curso> findAll() {
		return em.createQuery("from Curso").getResultList();
	}

	@Override
	@Transactional
	public void save(Curso curso) {
		em.merge(curso);
	}

	@Override
	public Curso findOne(Long id) {
		return em.find(Curso.class, id);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		em.remove(findOne(id));
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Curso> findAllByUserId(String username) {
		return em.createQuery("from Curso where username like :u").setParameter("u", username).getResultList();
	}

}
