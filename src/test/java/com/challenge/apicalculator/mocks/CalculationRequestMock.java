package com.challenge.apicalculator.mocks;

import com.challenge.apicalculator.model.dto.CalculationRequest;

public class CalculationRequestMock {
    
    public static CalculationRequest.CalculationRequestBuilder mock() {
        return CalculationRequest.builder()
                .num1(10.0)
                .num2(20.0);
    }
} 