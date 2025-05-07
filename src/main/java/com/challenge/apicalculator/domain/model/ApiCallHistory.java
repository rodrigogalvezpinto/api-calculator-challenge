package com.challenge.apicalculator.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entidad de dominio que representa un registro en el historial de llamadas a la API
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiCallHistory {
    private Long id;
    private String endpoint;
    private String parameters;
    private String response;
    private boolean successful;
    private String errorMessage;
    private LocalDateTime timestamp;
} 