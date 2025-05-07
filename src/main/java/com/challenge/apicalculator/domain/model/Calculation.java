package com.challenge.apicalculator.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad de dominio que representa un cálculo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Calculation {
    private double number1;
    private double number2;
    private double percentage;
    private double result;
} 