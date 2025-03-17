package com.challenge.apicalculator.service;

import com.challenge.apicalculator.config.CacheConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Implementación mock del servicio de porcentaje externo para desarrollo.
 * Esta implementación se usa cuando la propiedad app.external-service.mock está habilitada.
 */
@Service
@Primary
@Slf4j
@ConditionalOnProperty(name = "app.external-service.mock", havingValue = "true", matchIfMissing = true)
public class MockExternalPercentageService extends ExternalPercentageService {

    /**
     * Constructor que pasa el RestTemplate requerido a la clase padre.
     * 
     * @param restTemplate El RestTemplate a utilizar
     */
    public MockExternalPercentageService(RestTemplate restTemplate) {
        super(restTemplate);
    }

    /**
     * Devuelve un porcentaje fijo para desarrollo.
     *
     * @return El porcentaje fijo (10.0)
     */
    @Override
    @Cacheable(value = CacheConfig.PERCENTAGE_CACHE, key = "'percentage'")
    public Double getPercentage() {
        log.info("Usando servicio mock para el porcentaje externo");
        return 10.0;
    }
} 