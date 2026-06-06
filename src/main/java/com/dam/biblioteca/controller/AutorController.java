package com.dam.biblioteca.controller;

import com.dam.biblioteca.model.Autor;
import com.dam.biblioteca.service.AutorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * Controlador REST para la entidad Autor.
 * RESPONSABILIDAD: Persona 2 (compañero).
 * Persona 1 crea el esqueleto básico para que el proyecto compile.
 * El compañero debe completar los endpoints de búsqueda (Módulo B).
 */
@RestController
@RequestMapping("/api/v1/autores")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    // GET /api/v1/autores — 200 OK + lista
    @GetMapping
    public ResponseEntity<List<Autor>> getAll() {
        return ResponseEntity.ok(autorService.findAll());
    }

    // GET /api/v1/autores/{id} — 200 OK / 404
    @GetMapping("/{id}")
    public ResponseEntity<Autor> getById(@PathVariable Long id) {
        return autorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/v1/autores — 201 Created
    @PostMapping
    public ResponseEntity<Autor> create(@Valid @RequestBody Autor autor) {
        Autor creado = autorService.save(autor);
        URI location = URI.create("/api/v1/autores/" + creado.getId());
        return ResponseEntity.created(location).body(creado);
    }

    // PUT /api/v1/autores/{id} — 200 OK / 404
    @PutMapping("/{id}")
    public ResponseEntity<Autor> update(@PathVariable Long id,
                                        @Valid @RequestBody Autor autor) {
        return autorService.update(id, autor)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/v1/autores/{id} — 204 No Content / 404
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (autorService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // TODO (Persona 2) — Módulo B: añadir endpoint GET /api/v1/autores/buscar
    // con @RequestParam nombre y nacionalidad opcionales
    @GetMapping("/buscar")
    public ResponseEntity<List<Autor>> buscar(
        @RequestParam(required = false) String nombre,
        @RequestParam(required = false) String nacionalidad) {

        if (nombre != null && !nombre.isBlank()) {
            return ResponseEntity.ok(autorService.findByNombre(nombre));
        }

        if (nacionalidad != null && !nacionalidad.isBlank()) {
            return ResponseEntity.ok(autorService.findByNacionalidad(nacionalidad));
        }

        return ResponseEntity.ok(autorService.findAll());
    }
}
