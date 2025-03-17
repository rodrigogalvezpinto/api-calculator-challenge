package com.challenge.apicalculator.service;

import com.challenge.apicalculator.exception.ExternalServiceException;
import com.challenge.apicalculator.mocks.CalculationRequestMock;
import com.challenge.apicalculator.model.dto.CalculationRequest;
import com.challenge.apicalculator.model.dto.CalculationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class CalculationServiceTest {

    @Mock
    private ExternalPercentageService externalPercentageService;

    @InjectMocks
    private CalculationService calculationService;

    private CalculationRequest request;

    @BeforeEach
    void setUp() {
        request = CalculationRequestMock.mock().build();
    }

    @Test
    void calculate_ShouldReturnCorrectResult_WhenExternalServiceReturnsPercentage() {
        // Given
        double percentage = 5.0;
        given(externalPercentageService.getPercentage()).willReturn(percentage);

        // When
        CalculationResponse response = calculationService.calculate(request);

        // Then
        then(response).isNotNull();
        then(response.getNum1()).isEqualTo(request.getNum1());
        then(response.getNum2()).isEqualTo(request.getNum2());
        then(response.getSum()).isEqualTo(30.0); // 10 + 20 = 30
        then(response.getPercentage()).isEqualTo(percentage);
        then(response.getResult()).isEqualTo(31.5); // 30 + (30 * 5 / 100) = 31.5

        verify(externalPercentageService, times(1)).getPercentage();
    }

    @Test
    void calculate_ShouldThrowException_WhenExternalServiceFails() {
        // Given
        given(externalPercentageService.getPercentage())
                .willThrow(new ExternalServiceException("Error en el servicio externo"));

        // When & Then
        assertThatThrownBy(() -> calculationService.calculate(request))
                .isInstanceOf(ExternalServiceException.class)
                .hasMessage("Error en el servicio externo");

        verify(externalPercentageService, times(1)).getPercentage();
    }
} 