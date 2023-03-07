package com.dam2.ud7.service;

import java.util.Objects;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dam2.ud7.models.Curso;
import com.dam2.ud7.models.Usuario;
import com.dam2.ud7.repos.CursoRepository;

@Repository
public class ServiceImplementationCurso implements CursoRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public <S extends Curso> S save(S entity) {
		return em.merge(entity);
	}

	@Override
	@Transactional
	public <S extends Curso> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public void delete(Curso entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional
	public void deleteAll(Iterable<? extends Curso> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<Curso> findById(Long id) {
		Curso c = em.find(Curso.class, id);
		return Optional.ofNullable(c);
	}

	@Override
	public boolean existsById(Long id) {
		return Objects.equals(em.find(Curso.class, id).getId(), id);
	}

	@Override
	public Iterable<Curso> findAll() {
		return em.createQuery("from Curso").getResultList();
	}

	@Override
	public Iterable<Curso> findAllById(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		return (long) em.createQuery("select count(u) from Curso u").getSingleResult();
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		em.remove(em.find(Curso.class, id));
	}

	@Override
	@Transactional
	public void deleteAllById(Iterable<? extends Long> ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Iterable<Curso> findAll(Sort sort) {
		return null;
	}

	@Override
	public Page<Curso> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<Curso> findAllByUsuario(Usuario usuario) {
		for (Curso x : em.createQuery("from Curso where id = :id", Curso.class).setParameter("id", usuario.getId()).getResultList()) {
			System.out.println(x.getTitulo());
		}
		return em.createQuery("from Curso where usuario_curso = :id").setParameter("id", usuario.getId()).getResultList();
	}


}
