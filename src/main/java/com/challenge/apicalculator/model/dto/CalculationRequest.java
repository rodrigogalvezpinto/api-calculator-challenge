package com.challenge.apicalculator.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalculationRequest {

    @NotNull(message = "El primer número no puede ser nulo")
    private Double num1;

    @NotNull(message = "El segundo número no puede ser nulo")
    private Double num2;
} 