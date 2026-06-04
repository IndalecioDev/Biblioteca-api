package com.dam.biblioteca.service;

import com.dam.biblioteca.model.Autor;
import com.dam.biblioteca.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Capa de servicio para la entidad Autor.
 * RESPONSABILIDAD: Persona 2 (compañero).
 * Persona 1 crea el esqueleto para que el proyecto compile.
 */
@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public List<Autor> findAll() {
        return autorRepository.findAll();
    }

    public Optional<Autor> findById(Long id) {
        return autorRepository.findById(id);
    }

    public Autor save(Autor autor) {
        return autorRepository.save(autor);
    }

    public Optional<Autor> update(Long id, Autor autorActualizado) {
        return autorRepository.findById(id).map(existente -> {
            existente.setNombre(autorActualizado.getNombre());
            existente.setNacionalidad(autorActualizado.getNacionalidad());
            existente.setAnioNacimiento(autorActualizado.getAnioNacimiento());
            return autorRepository.save(existente);
        });
    }

    public boolean deleteById(Long id) {
        if (autorRepository.existsById(id)) {
            autorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Autor> findByNombre(String nombre) {
        return autorRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public List<Autor> findByNacionalidad(String nacionalidad) {
        return autorRepository.findByNacionalidad(nacionalidad);
    }
}
