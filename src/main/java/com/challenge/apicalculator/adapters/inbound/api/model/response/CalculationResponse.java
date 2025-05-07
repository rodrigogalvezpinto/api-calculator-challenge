package com.challenge.apicalculator.adapters.inbound.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para la respuesta de cálculo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalculationResponse {
    private double result;
    private double number1;
    private double number2;
    private double percentage;
} 