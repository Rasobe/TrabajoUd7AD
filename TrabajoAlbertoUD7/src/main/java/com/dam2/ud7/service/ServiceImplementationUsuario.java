package com.dam2.ud7.service;

import java.util.Objects;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dam2.ud7.models.Usuario;
import com.dam2.ud7.repos.UsuarioRepository;

@Repository
public class ServiceImplementationUsuario implements UsuarioRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public <S extends Usuario> S save(S entity) {
		return em.merge(entity);
	}

	@Override
	public <S extends Usuario> Iterable<S> saveAll(Iterable<S> entities) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Optional<Usuario> findById(Long id) {
		Usuario u = em.find(Usuario.class, id);
		System.out.println(u.getEmail());
		return Optional.ofNullable(u);

	}

	@Override
	public boolean existsById(Long id) {
		return Objects.equals(em.find(Usuario.class, id).getId(), id);
	}

	@Override
	public Iterable<Usuario> findAll() {
		return em.createQuery("from Usuario").getResultList();
	}

	@Override
	public Iterable<Usuario> findAllById(Iterable<Long> ids) {
		return null;
	}

	@Override
	public long count() {
		return (long) em.createQuery("select count(u) from Usuario u").getSingleResult();
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		em.remove(em.find(Usuario.class, id));
	}

	@Override
	public void delete(Usuario entity) {
		em.remove(entity);
	}

	@Override
	public void deleteAllById(Iterable<? extends Long> ids) {

	}

	@Override
	public void deleteAll(Iterable<? extends Usuario> entities) {

	}

	@Override
	public void deleteAll() {

	}

	@Override
	public Iterable<Usuario> findAll(Sort sort) {
		return null;
	}

	@Override
	public Page<Usuario> findAll(Pageable pageable) {
		return null;
	}

	@Override
	public Optional<Usuario> findByUsername(String username) {
		Usuario u = (Usuario) em.createQuery("from Usuario where username = :u").setParameter("u", username)
				.getSingleResult();
		System.out.println(u.getEmail());
		return Optional.ofNullable(u);
	}

}
