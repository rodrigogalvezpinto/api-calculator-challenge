package com.challenge.apicalculator.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalculationResponse {

    private Double num1;
    private Double num2;
    private Double sum;
    private Double percentage;
    private Double result;
} 