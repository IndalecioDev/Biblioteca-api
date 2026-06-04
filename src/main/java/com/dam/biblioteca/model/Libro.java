package com.dam.biblioteca.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Entidad JPA que representa un Libro.
 * RESPONSABILIDAD: Persona 1 (Indalecio).
 *
 * Anotaciones Lombok que genera @Data:
 *   - @Getter / @Setter para todos los campos
 *   - @ToString con todos los campos (excepto los excluidos)
 *   - @EqualsAndHashCode basado en todos los campos
 *   - @RequiredArgsConstructor (constructor con campos @NonNull o final)
 */
@Entity
@Table(name = "libros")
@Data
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título no puede estar vacío")
    @Size(max = 200, message = "El título no puede superar los 200 caracteres")
    @Column(nullable = false)
    private String titulo;

    @Size(max = 13, message = "El ISBN no puede superar los 13 caracteres")
    @Column(unique = true)
    private String isbn;

    @Column(name = "anio_publicacion")
    private Integer anioPublicacion;

    @Column(length = 500)
    private String descripcion;

    private Boolean disponible = true;

    /**
     * Lado "muchos" de la relación @ManyToOne con Autor.
     * @JoinColumn define explícitamente el nombre de la FK en la tabla libros.
     * La FK aparece en la BD como columna autor_id.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Autor autor;
}
