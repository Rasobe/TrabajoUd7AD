package com.dam2.ud7.repos;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.dam2.ud7.models.Curso;
import com.dam2.ud7.models.Usuario;

// Ya que JpaRepository está obsoleto, se usa este 'CrudRepository'.

@Repository
public interface CursoRepository extends CrudRepository<Curso, Long>, PagingAndSortingRepository<Curso, Long> {

	public Iterable<Curso> findAllByUsuario(Usuario usuario);
	
	public Page<Curso> findAllByUsuario(org.springframework.data.domain.Pageable pageable, Usuario usuario);

}