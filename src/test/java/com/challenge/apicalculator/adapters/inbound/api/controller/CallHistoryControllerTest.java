package com.challenge.apicalculator.adapters.inbound.api.controller;

import com.challenge.apicalculator.adapters.inbound.api.model.response.ApiCallHistoryResponse;
import com.challenge.apicalculator.domain.service.ApiCallHistoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
class CallHistoryControllerTest {

    @Mock
    private ApiCallHistoryService apiCallHistoryService;

    @InjectMocks
    private CallHistoryController callHistoryController;

    @Test
    void getHistory_CuandoHayHistorial_DebeRetornarLista() throws Exception {
        // Given
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(callHistoryController).build();
        ApiCallHistoryResponse response = ApiCallHistoryResponse.builder()
                .endpoint("/api/v1/calculations")
                .parameters("{\"number1\":10,\"number2\":20}")
                .response("{\"result\":33.0,\"percentage\":10.0}")
                .errorMessage(null)
                .successful(true)
                .timestamp(LocalDateTime.now())
                .build();
        given(apiCallHistoryService.getApiCallHistory()).willReturn(List.of(response));

        // When & Then
        mockMvc.perform(get("/api/v1/history")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].endpoint").value("/api/v1/calculations"));
    }

    @Test
    void getHistoryByEndpoint_CuandoHayHistorial_DebeRetornarLista() throws Exception {
        // Given
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(callHistoryController).build();
        ApiCallHistoryResponse response = ApiCallHistoryResponse.builder()
                .endpoint("/api/v1/calculations")
                .parameters("{\"number1\":10,\"number2\":20}")
                .response("{\"result\":33.0,\"percentage\":10.0}")
                .errorMessage(null)
                .successful(true)
                .timestamp(LocalDateTime.now())
                .build();
        given(apiCallHistoryService.getApiCallHistoryByEndpoint(any())).willReturn(List.of(response));

        // When & Then
        mockMvc.perform(get("/api/v1/history/endpoint/test")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].endpoint").value("/api/v1/calculations"));
    }
} 