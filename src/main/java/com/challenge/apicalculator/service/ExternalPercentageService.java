package com.challenge.apicalculator.service;

import com.challenge.apicalculator.config.CacheConfig;
import com.challenge.apicalculator.exception.ExternalServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(name = "app.external-service.mock", havingValue = "false")
public class ExternalPercentageService {

    private final RestTemplate restTemplate;
    
    @Value("${app.external-service.url}")
    private String externalServiceUrl;
    
    protected Double lastPercentage = null;

    /**
     * Obtiene el porcentaje del servicio externo.
     * Si el servicio externo falla, se usa el último valor almacenado.
     * Si no hay valor almacenado, se lanza una excepción.
     *
     * @return El porcentaje obtenido
     * @throws ExternalServiceException Si el servicio externo falla y no hay valor en caché
     */
    @Cacheable(value = CacheConfig.PERCENTAGE_CACHE, key = "'percentage'")
    public Double getPercentage() {
        try {
            // Llamada al servicio externo con tipos genéricos adecuados
            ResponseEntity<Map<String, Double>> responseEntity = restTemplate.exchange(
                    externalServiceUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Map<String, Double>>() {}
            );
            
            Map<String, Double> response = responseEntity.getBody();
            Double percentage = Optional.ofNullable(response)
                    .map(map -> map.get("percentage"))
                    .orElseThrow(() -> new ExternalServiceException("El servicio externo no devolvió un porcentaje válido"));
            
            // Guardar el último porcentaje obtenido
            lastPercentage = percentage;
            log.info("Porcentaje obtenido del servicio externo: {}", percentage);
            return percentage;
        } catch (RestClientException e) {
            log.error("Error al obtener el porcentaje del servicio externo", e);
            
            // Si hay un valor en caché, usarlo
            if (lastPercentage != null) {
                log.info("Usando el último porcentaje almacenado: {}", lastPercentage);
                return lastPercentage;
            }
            
            // Si no hay valor en caché, lanzar excepción
            throw new ExternalServiceException("No se pudo obtener el porcentaje del servicio externo y no hay valor en caché");
        }
    }
} 