package com.challenge.apicalculator.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "api_call_history")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiCallHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private String endpoint;

    @Column(length = 1000)
    private String parameters;

    @Column(length = 1000)
    private String response;

    @Column(length = 1000)
    private String errorMessage;

    @Column(nullable = false)
    private boolean successful;
} 