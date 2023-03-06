package com.dam2.ud7.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.dam2.ud7.models.Usuario;

// Ya que JpaRepository está obsoleto, se usa este 'CrudRepository'.

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long>, PagingAndSortingRepository<Usuario, Long> {

}