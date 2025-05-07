package com.challenge.apicalculator.adapters.outbound.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entidad de persistencia para el historial de llamadas a la API
 */
@Entity
@Table(name = "api_call_history")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiCallHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String endpoint;

    @Column(columnDefinition = "TEXT")
    private String parameters;

    @Column(columnDefinition = "TEXT")
    private String response;

    @Column(nullable = false)
    private boolean successful;

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;

    @Column(nullable = false)
    private LocalDateTime timestamp;
} 