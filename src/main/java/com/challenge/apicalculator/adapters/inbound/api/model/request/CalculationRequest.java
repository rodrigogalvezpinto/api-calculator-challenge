package com.challenge.apicalculator.adapters.inbound.api.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para la solicitud de cálculo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalculationRequest {
    
    @NotNull(message = "El primer número es requerido")
    private Double number1;
    
    @NotNull(message = "El segundo número es requerido")
    private Double number2;
} 