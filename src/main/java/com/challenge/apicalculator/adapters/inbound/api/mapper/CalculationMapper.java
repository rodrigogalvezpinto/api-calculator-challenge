package com.challenge.apicalculator.adapters.inbound.api.mapper;

import com.challenge.apicalculator.adapters.inbound.api.model.request.CalculationRequest;
import com.challenge.apicalculator.adapters.inbound.api.model.response.CalculationResponse;
import com.challenge.apicalculator.domain.model.Calculation;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre DTOs y entidades de cálculo
 */
@Component
public class CalculationMapper implements BaseMapper<CalculationResponse, Calculation> {

    @Override
    public CalculationResponse toDto(Calculation entity) {
        if (entity == null) {
            return null;
        }

        return CalculationResponse.builder()
            .result(entity.getResult())
            .number1(entity.getNumber1())
            .number2(entity.getNumber2())
            .percentage(entity.getPercentage())
            .build();
    }

    @Override
    public Calculation toEntity(CalculationResponse dto) {
        if (dto == null) {
            return null;
        }

        return Calculation.builder()
            .result(dto.getResult())
            .number1(dto.getNumber1())
            .number2(dto.getNumber2())
            .percentage(dto.getPercentage())
            .build();
    }

    /**
     * Convierte un request a entidad de cálculo
     */
    public Calculation toEntity(CalculationRequest request) {
        if (request == null) {
            return null;
        }

        return Calculation.builder()
            .number1(request.getNumber1())
            .number2(request.getNumber2())
            .build();
    }
} 