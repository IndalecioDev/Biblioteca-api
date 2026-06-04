package com.dam.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Entidad JPA que representa un Autor.
 * PARTE DEL COMPAÑERO: esta clase es responsabilidad de Persona 2.
 * Persona 1 la crea como esqueleto para que compile la relación con Libro.
 */
@Entity
@Table(name = "autores")
@Data
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    @Column(nullable = false)
    private String nombre;

    @Size(max = 100)
    private String nacionalidad;

    @Column(name = "anio_nacimiento")
    private Integer anioNacimiento;

    /**
     * Lado "uno" de la relación @OneToMany con Libro.
     * @JsonIgnore evita la referencia circular al serializar: Autor → libros → autor → libros...
     * @ToString.Exclude evita el StackOverflowError en Lombok al llamar a toString().
     */
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<Libro> libros;
}
