package com.dam.biblioteca.controller;

import com.dam.biblioteca.model.Libro;
import com.dam.biblioteca.service.LibroService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * Controlador REST para la entidad Libro.
 * RESPONSABILIDAD: Persona 1 (Indalecio).
 *
 * @RestController = @Controller + @ResponseBody (serializa respuestas a JSON automáticamente).
 * @RequestMapping define el prefijo base de todas las rutas.
 * El Controller NUNCA inyecta ni llama al Repository directamente — siempre usa el Service.
 */
@RestController
@RequestMapping("/api/v1/libros")
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    // ─── CRUD ────────────────────────────────────────────────────────────────────

    /**
     * GET /api/v1/libros
     * Devuelve todos los libros.
     * Respuesta: 200 OK + lista JSON
     */
    @GetMapping
    public ResponseEntity<List<Libro>> getAll() {
        return ResponseEntity.ok(libroService.findAll());
    }

    /**
     * GET /api/v1/libros/{id}
     * Devuelve un libro por ID.
     * Respuesta: 200 OK si existe / 404 Not Found si no
     *
     * Optional.map(ResponseEntity::ok) — si el Optional tiene valor, lo envuelve en 200 OK.
     * .orElse(ResponseEntity.notFound().build()) — si está vacío, devuelve 404.
     * NUNCA usamos .get() sin comprobar antes: ese patrón lanzaría NoSuchElementException.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Libro> getById(@PathVariable Long id) {
        return libroService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/v1/libros
     * Crea un nuevo libro.
     * @Valid activa las validaciones de la entidad (@NotBlank, @Size...).
     * Respuesta: 201 Created + Location header + cuerpo del libro creado
     */
    @PostMapping
    public ResponseEntity<Libro> create(@Valid @RequestBody Libro libro) {
        Libro creado = libroService.save(libro);
        URI location = URI.create("/api/v1/libros/" + creado.getId());
        return ResponseEntity.created(location).body(creado);
    }

    /**
     * PUT /api/v1/libros/{id}
     * Actualiza un libro existente.
     * Respuesta: 200 OK si existe / 404 Not Found si no
     */
    @PutMapping("/{id}")
    public ResponseEntity<Libro> update(@PathVariable Long id,
                                        @Valid @RequestBody Libro libro) {
        return libroService.update(id, libro)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /api/v1/libros/{id}
     * Elimina un libro por ID.
     * Respuesta: 204 No Content si existía / 404 Not Found si no
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (libroService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // ─── Módulo A — endpoint de relación ─────────────────────────────────────────

    /**
     * GET /api/v1/libros/autor/{autorId}
     * Devuelve todos los libros de un autor concreto.
     * Usa la FK autor_id para filtrar — demuestra que la relación está implementada.
     * Respuesta: 200 OK + lista (puede estar vacía)
     */
    @GetMapping("/autor/{autorId}")
    public ResponseEntity<List<Libro>> getByAutor(@PathVariable Long autorId) {
        return ResponseEntity.ok(libroService.findByAutorId(autorId));
    }

    // ─── Módulo B — búsqueda con @RequestParam ────────────────────────────────────

    /**
     * GET /api/v1/libros/buscar?titulo=xxx&disponible=true&anio=2020
     *
     * @RequestParam(required = false) — todos los parámetros son opcionales.
     * Si se pasa 'titulo', filtra por título.
     * Si se pasa 'disponible', filtra por disponibilidad.
     * Si se pasa 'anio', filtra por año de publicación.
     * Si no se pasa ninguno, devuelve todos.
     *
     * En la defensa mostrar 3 llamadas distintas cambiando los params.
     * Activar spring.jpa.show-sql=true para mostrar el SQL generado en consola.
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Libro>> buscar(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) Boolean disponible,
            @RequestParam(required = false) Integer anio) {

        if (titulo != null && !titulo.isBlank()) {
            return ResponseEntity.ok(libroService.findByTitulo(titulo));
        }
        if (disponible != null) {
            return ResponseEntity.ok(libroService.findByDisponible(disponible));
        }
        if (anio != null) {
            return ResponseEntity.ok(libroService.findByAnio(anio));
        }
        return ResponseEntity.ok(libroService.findAll());
    }
}
