package com.dam.biblioteca.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Manejador global de excepciones — Módulo D (calidad).
 * RESPONSABILIDAD: Persona 1 (Indalecio).
 *
 * @RestControllerAdvice intercepta excepciones de cualquier controlador
 * y devuelve respuestas de error consistentes en JSON en lugar del
 * error HTML genérico de Spring Boot.
 *
 * En la defensa: explicar qué hace @ControllerAdvice, por qué se usa
 * y cómo mejora la experiencia del consumidor de la API.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Captura errores de validación (@Valid en el Controller).
     * Devuelve 400 Bad Request con la lista de campos inválidos.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        List<String> errores = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .collect(Collectors.toList());

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("timestamp", LocalDateTime.now().toString());
        respuesta.put("status", 400);
        respuesta.put("error", "Validación fallida");
        respuesta.put("mensajes", errores);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }

    /**
     * Captura cualquier excepción no controlada.
     * Devuelve 500 Internal Server Error con mensaje genérico.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericError(Exception ex) {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("timestamp", LocalDateTime.now().toString());
        respuesta.put("status", 500);
        respuesta.put("error", "Error interno del servidor");
        respuesta.put("mensaje", ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
    }
}
