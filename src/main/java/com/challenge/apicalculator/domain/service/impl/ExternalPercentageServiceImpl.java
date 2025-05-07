package com.challenge.apicalculator.domain.service.impl;

import com.challenge.apicalculator.domain.exception.PercentageServiceException;
import com.challenge.apicalculator.domain.service.PercentageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

@Service
@Slf4j
@Primary
@ConditionalOnProperty(name = "app.external-service.mock", havingValue = "false")
public class ExternalPercentageServiceImpl implements PercentageService {

    private final RestTemplate restTemplate;
    private final String externalServiceUrl;

    public ExternalPercentageServiceImpl(
            RestTemplate restTemplate,
            @Value("${app.external-service.url}") String externalServiceUrl) {
        this.restTemplate = restTemplate;
        this.externalServiceUrl = externalServiceUrl;
    }

    @Override
    @Cacheable(value = "percentage", unless = "#result == null", key = "'current_percentage'")
    public double getDynamicPercentage() {
        try {
            log.info("Obteniendo porcentaje del servicio externo");
            var response = restTemplate.getForObject(externalServiceUrl, PercentageResponse.class);
            if (response != null && response.getPercentage() != null) {
                log.info("Porcentaje obtenido exitosamente: {}", response.getPercentage());
                return response.getPercentage();
            }
            throw new PercentageServiceException("Respuesta inválida del servicio externo");
        } catch (RestClientException e) {
            log.error("Error al obtener el porcentaje del servicio externo: {}", e.getMessage());
            throw new PercentageServiceException("No se pudo obtener el porcentaje dinámico porque el servicio externo no está disponible. Por favor, intente más tarde.", e);
        }
    }

    @CachePut(value = "percentage", key = "'current_percentage'")
    public double updateCachedPercentage(double percentage) {
        log.info("Actualizando porcentaje en caché: {}", percentage);
        return percentage;
    }

    private static class PercentageResponse {
        private Double percentage;

        public Double getPercentage() {
            return percentage;
        }

        public void setPercentage(Double percentage) {
            this.percentage = percentage;
        }
    }
} 