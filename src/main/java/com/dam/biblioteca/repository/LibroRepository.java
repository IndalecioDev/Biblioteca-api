package com.dam.biblioteca.repository;

import com.dam.biblioteca.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para la entidad Libro.
 * RESPONSABILIDAD: Persona 1 (Indalecio).
 *
 * Extiende JpaRepository<Libro, Long> — Spring genera automáticamente en tiempo de ejecución
 * la implementación de todos los métodos estándar (findAll, findById, save, delete...).
 *
 * Los métodos derivados (findBy...) son parseados por Spring Data y convertidos a SQL sin
 * necesidad de escribir ninguna query manualmente.
 */
@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    /**
     * Módulo B — búsqueda por título (contiene, sin distinción de mayúsculas).
     * Spring traduce esto a: WHERE LOWER(titulo) LIKE LOWER('%:titulo%')
     */
    List<Libro> findByTituloContainingIgnoreCase(String titulo);

    /**
     * Módulo B — filtrar libros por disponibilidad.
     */
    List<Libro> findByDisponible(Boolean disponible);

    /**
     * Módulo B — filtrar libros por año de publicación.
     */
    List<Libro> findByAnioPublicacion(Integer anio);

    /**
     * Módulo A — obtener todos los libros de un autor concreto por su ID.
     * Usado en el endpoint GET /api/v1/libros/autor/{autorId}
     */
    List<Libro> findByAutorId(Long autorId);
}
