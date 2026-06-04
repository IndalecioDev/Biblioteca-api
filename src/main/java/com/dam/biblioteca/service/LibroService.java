package com.dam.biblioteca.service;

import com.dam.biblioteca.model.Libro;
import com.dam.biblioteca.repository.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Capa de servicio para la entidad Libro.
 * RESPONSABILIDAD: Persona 1 (Indalecio).
 *
 * Esta capa contiene la lógica de negocio. El Controller llama al Service,
 * y el Service llama al Repository. El Controller NUNCA accede al Repository directamente.
 *
 * @Service marca esta clase como un bean de servicio gestionado por Spring.
 */
@Service
public class LibroService {

    private final LibroRepository libroRepository;

    // Inyección de dependencias por constructor (recomendada sobre @Autowired en campo)
    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    // ─── CRUD básico ────────────────────────────────────────────────────────────

    /**
     * Devuelve todos los libros.
     */
    public List<Libro> findAll() {
        return libroRepository.findAll();
    }

    /**
     * Busca un libro por ID.
     * Devuelve Optional<Libro> — el controlador decide qué hacer si no existe.
     */
    public Optional<Libro> findById(Long id) {
        return libroRepository.findById(id);
    }

    /**
     * Guarda un nuevo libro o actualiza uno existente.
     */
    public Libro save(Libro libro) {
        return libroRepository.save(libro);
    }

    /**
     * Actualiza un libro existente. Devuelve Optional vacío si no existe.
     */
    public Optional<Libro> update(Long id, Libro libroActualizado) {
        return libroRepository.findById(id).map(libroExistente -> {
            libroExistente.setTitulo(libroActualizado.getTitulo());
            libroExistente.setIsbn(libroActualizado.getIsbn());
            libroExistente.setAnioPublicacion(libroActualizado.getAnioPublicacion());
            libroExistente.setDescripcion(libroActualizado.getDescripcion());
            libroExistente.setDisponible(libroActualizado.getDisponible());
            libroExistente.setAutor(libroActualizado.getAutor());
            return libroRepository.save(libroExistente);
        });
    }

    /**
     * Elimina un libro por ID. Devuelve true si existía, false si no.
     */
    public boolean deleteById(Long id) {
        if (libroRepository.existsById(id)) {
            libroRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // ─── Módulo A — relaciones ───────────────────────────────────────────────────

    /**
     * Obtiene todos los libros de un autor concreto.
     */
    public List<Libro> findByAutorId(Long autorId) {
        return libroRepository.findByAutorId(autorId);
    }

    // ─── Módulo B — búsqueda con parámetros ─────────────────────────────────────

    /**
     * Busca libros por título (parcial, case-insensitive).
     */
    public List<Libro> findByTitulo(String titulo) {
        return libroRepository.findByTituloContainingIgnoreCase(titulo);
    }

    /**
     * Filtra libros por disponibilidad.
     */
    public List<Libro> findByDisponible(Boolean disponible) {
        return libroRepository.findByDisponible(disponible);
    }

    /**
     * Filtra libros por año de publicación.
     */
    public List<Libro> findByAnio(Integer anio) {
        return libroRepository.findByAnioPublicacion(anio);
    }
}
