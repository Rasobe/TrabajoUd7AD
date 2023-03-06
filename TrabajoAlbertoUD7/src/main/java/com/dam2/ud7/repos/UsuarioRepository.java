package com.dam2.ud7.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.dam2.ud7.entity.Usuario;

// Ya que JpaRepository está obsoleto, se usa este 'CrudRepository'.

public interface UsuarioRepository extends CrudRepository<Usuario, Long>, PagingAndSortingRepository<Usuario, Long> {

	Usuario findByUsername(String username);

}