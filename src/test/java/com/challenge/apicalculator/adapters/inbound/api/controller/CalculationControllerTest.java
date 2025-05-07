package com.challenge.apicalculator.adapters.inbound.api.controller;

import com.challenge.apicalculator.adapters.inbound.api.model.request.CalculationRequest;
import com.challenge.apicalculator.adapters.inbound.api.model.response.CalculationResponse;
import com.challenge.apicalculator.domain.service.CalculationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.assertj.core.api.BDDAssertions.then;

@ExtendWith(MockitoExtension.class)
class CalculationControllerTest {

    @Mock
    private CalculationService calculationService;

    @InjectMocks
    private CalculationController calculationController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void calculate_CuandoRequestValido_DebeRetornarResultado() throws Exception {
        // Given
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(calculationController).build();
        CalculationRequest request = CalculationRequest.builder().number1(10.0).number2(20.0).build();
        CalculationResponse response = CalculationResponse.builder().result(33.0).percentage(10.0).build();
        given(calculationService.calculate(any(CalculationRequest.class))).willReturn(response);

        // When & Then
        mockMvc.perform(post("/api/v1/calculations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(33.0))
                .andExpect(jsonPath("$.percentage").value(10.0));
    }

    @Test
    void calculate_CuandoRequestInvalido_DebeRetornarBadRequest() throws Exception {
        // Given
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(calculationController).build();
        String invalidRequest = "{}"; // Falta number1 y number2

        // When & Then
        mockMvc.perform(post("/api/v1/calculations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequest))
                .andExpect(status().isBadRequest());
    }
} 