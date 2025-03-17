package com.challenge.apicalculator.service;

import com.challenge.apicalculator.model.dto.CalculationRequest;
import com.challenge.apicalculator.model.dto.CalculationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalculationService {

    private final ExternalPercentageService externalPercentageService;

    /**
     * Realiza el cálculo: suma num1 y num2, y aplica el porcentaje obtenido del servicio externo.
     *
     * @param request La solicitud con los números a calcular
     * @return La respuesta con el resultado del cálculo
     */
    public CalculationResponse calculate(CalculationRequest request) {
        log.info("Realizando cálculo con números: {} y {}", request.getNum1(), request.getNum2());
        
        // Obtener el porcentaje del servicio externo
        Double percentage = externalPercentageService.getPercentage();
        
        // Realizar el cálculo
        Double sum = request.getNum1() + request.getNum2();
        Double result = sum + (sum * percentage / 100);
        
        // Construir la respuesta
        return CalculationResponse.builder()
                .num1(request.getNum1())
                .num2(request.getNum2())
                .sum(sum)
                .percentage(percentage)
                .result(result)
                .build();
    }
} 