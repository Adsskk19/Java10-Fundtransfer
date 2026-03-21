package com.example.fundtransfer.controller;

import com.example.fundtransfer.dto.AccountDTO;
import com.example.fundtransfer.exception.AccountNotFoundException;
import com.example.fundtransfer.service.AccountService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @Test
    void getAccountsForValidCustomerId_ReturnsAccounts() {

        // Arrange
        Long customerId = 1L;

        AccountDTO accountDTO = new AccountDTO(
                101L,
                987456321L,
                "1234567890",
                new BigDecimal("10000.00"),
                null
        );

        List<AccountDTO> accounts = Arrays.asList(accountDTO);

        when(accountService.getAccountsForUser(customerId))
                .thenReturn(accounts);

        // Act
        List<AccountDTO> response =
                accountController.getAccounts(customerId);

        // Assert
        /*assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(101L, response.getBody().get(0).getId());*/

        verify(accountService).getAccountsForUser(customerId);
    }

    @Test
    void getAccountsForNonExistentCustomerId_ReturnsException() {

        // Arrange
        Long customerId = 999L;

        when(accountService.getAccountsForUser(customerId))
                .thenThrow(new AccountNotFoundException(
                        "No accounts found for customer: " + customerId
                ));

        // Act & Assert
        Exception ex = assertThrows(AccountNotFoundException.class, () ->
                accountController.getAccounts(customerId)
        );

        assertEquals("No accounts found for customer: 999", ex.getMessage());

        verify(accountService).getAccountsForUser(customerId);
    }
}