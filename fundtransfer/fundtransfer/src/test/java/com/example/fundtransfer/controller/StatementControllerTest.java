package com.example.fundtransfer.controller;

import com.example.fundtransfer.dto.StatementResponseDTO;
import com.example.fundtransfer.service.StatementService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class StatementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatementService statementService;

    @Test
    void shouldReturnStatementsSuccessfully() throws Exception {

        StatementResponseDTO dto = new StatementResponseDTO();
        dto.setTransactionId(1L);
        dto.setAmount(500.0);
        dto.setDescription("Test Transaction");
        dto.setTransactionDate(LocalDateTime.now());

        when(statementService.getStatements(1L))
                .thenReturn(List.of(dto));
        mockMvc.perform(get("/accounts/1/statements"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].transactionId").value(1L))
                .andExpect(jsonPath("$[0].amount").value(500.0))
                .andExpect(jsonPath("$[0].type").value("DEBIT"));
    }
}