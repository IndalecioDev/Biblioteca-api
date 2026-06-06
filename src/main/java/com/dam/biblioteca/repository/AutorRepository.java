package com.dam.biblioteca.repository;

import com.dam.biblioteca.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para la entidad Autor.
 * RESPONSABILIDAD: Persona 2 (compañero).
 * Persona 1 crea el esqueleto para que el proyecto compile.
 */
@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    /**
     * Módulo B — búsqueda de autores por nombre (contiene, case-insensitive).
     */
    List<Autor> findByNombreContainingIgnoreCase(String nombre);

    /**
     * Búsqueda por nacionalidad.
     */
    List<Autor> findByNacionalidadContainingIgnoreCase(String nacionalidad);
}
