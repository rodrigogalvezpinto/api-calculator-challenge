package com.challenge.apicalculator.adapters.inbound.api.util;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

/**
 * Utilidades para manejar respuestas HTTP
 */
public final class ResponseEntityUtils {
    
    private ResponseEntityUtils() {
        throw new IllegalStateException("Clase de utilidad");
    }

    /**
     * Retorna 200 OK si el optional tiene valor, o 404 Not Found si está vacío
     */
    public static <T> ResponseEntity<T> okOrNotFound(Optional<T> optional) {
        return optional.map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Retorna 200 OK con una página de resultados
     */
    public static <T> ResponseEntity<Page<T>> ok(Page<T> page) {
        return ResponseEntity.ok(page);
    }

    /**
     * Retorna 200 OK con una lista de resultados
     */
    public static <T> ResponseEntity<List<T>> ok(List<T> list) {
        return ResponseEntity.ok(list);
    }

    /**
     * Retorna 201 Created con el cuerpo de la respuesta
     */
    public static <T> ResponseEntity<T> created(T body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    /**
     * Retorna 204 No Content
     */
    public static ResponseEntity<Void> noContent() {
        return ResponseEntity.noContent().build();
    }
} 