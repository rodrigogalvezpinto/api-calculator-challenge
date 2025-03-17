package com.challenge.apicalculator.service;

import com.challenge.apicalculator.exception.ExternalServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ExternalPercentageServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ExternalPercentageService externalPercentageService;

    private final String externalServiceUrl = "http://localhost:8081/api/percentage";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(externalPercentageService, "externalServiceUrl", externalServiceUrl);
    }

    @Test
    void getPercentage_ShouldReturnPercentage_WhenExternalServiceRespondsCorrectly() {
        // Given
        Map<String, Double> responseBody = new HashMap<>();
        responseBody.put("percentage", 5.0);
        
        ResponseEntity<Map<String, Double>> responseEntity = ResponseEntity.ok(responseBody);
        
        given(restTemplate.exchange(
                eq(externalServiceUrl),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class)))
                .willReturn(responseEntity);

        // When
        Double percentage = externalPercentageService.getPercentage();

        // Then
        then(percentage).isNotNull();
        then(percentage).isEqualTo(5.0);

        verify(restTemplate, times(1)).exchange(
                eq(externalServiceUrl),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class));
    }

    @Test
    void getPercentage_ShouldThrowException_WhenExternalServiceReturnsNull() {
        // Given
        given(restTemplate.exchange(
                eq(externalServiceUrl),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class)))
                .willReturn(ResponseEntity.ok(null));

        // When & Then
        assertThatThrownBy(() -> externalPercentageService.getPercentage())
                .isInstanceOf(ExternalServiceException.class)
                .hasMessageContaining("El servicio externo no devolvió un porcentaje válido");

        verify(restTemplate, times(1)).exchange(
                eq(externalServiceUrl),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class));
    }

    @Test
    void getPercentage_ShouldThrowException_WhenExternalServiceReturnsEmptyMap() {
        // Given
        Map<String, Double> responseBody = new HashMap<>();
        ResponseEntity<Map<String, Double>> responseEntity = ResponseEntity.ok(responseBody);
        
        given(restTemplate.exchange(
                eq(externalServiceUrl),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class)))
                .willReturn(responseEntity);

        // When & Then
        assertThatThrownBy(() -> externalPercentageService.getPercentage())
                .isInstanceOf(ExternalServiceException.class)
                .hasMessageContaining("El servicio externo no devolvió un porcentaje válido");

        verify(restTemplate, times(1)).exchange(
                eq(externalServiceUrl),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class));
    }

    @Test
    void getPercentage_ShouldUseLastPercentage_WhenExternalServiceFails() {
        // Given
        // Primera llamada exitosa
        Map<String, Double> responseBody = new HashMap<>();
        responseBody.put("percentage", 5.0);
        ResponseEntity<Map<String, Double>> responseEntity = ResponseEntity.ok(responseBody);
        
        given(restTemplate.exchange(
                eq(externalServiceUrl),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class)))
                .willReturn(responseEntity)
                .willThrow(new RestClientException("Error en el servicio externo"));

        // When
        Double firstPercentage = externalPercentageService.getPercentage();
        Double secondPercentage = externalPercentageService.getPercentage();

        // Then
        then(firstPercentage).isNotNull();
        then(firstPercentage).isEqualTo(5.0);
        then(secondPercentage).isNotNull();
        then(secondPercentage).isEqualTo(5.0); // Debe usar el último valor almacenado

        verify(restTemplate, times(2)).exchange(
                eq(externalServiceUrl),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class));
    }

    @Test
    void getPercentage_ShouldThrowException_WhenExternalServiceFailsAndNoLastPercentage() {
        // Given
        given(restTemplate.exchange(
                eq(externalServiceUrl),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class)))
                .willThrow(new RestClientException("Error en el servicio externo"));

        // When & Then
        assertThatThrownBy(() -> externalPercentageService.getPercentage())
                .isInstanceOf(ExternalServiceException.class)
                .hasMessageContaining("No se pudo obtener el porcentaje del servicio externo y no hay valor en caché");

        verify(restTemplate, times(1)).exchange(
                eq(externalServiceUrl),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class));
    }
} 