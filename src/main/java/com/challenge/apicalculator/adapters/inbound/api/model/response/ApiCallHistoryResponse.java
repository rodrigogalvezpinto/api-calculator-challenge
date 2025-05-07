package com.challenge.apicalculator.adapters.inbound.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO para la respuesta del historial de llamadas
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiCallHistoryResponse {
    private Long id;
    private String endpoint;
    private String parameters;
    private String response;
    private boolean successful;
    private String errorMessage;
    private LocalDateTime timestamp;
} 