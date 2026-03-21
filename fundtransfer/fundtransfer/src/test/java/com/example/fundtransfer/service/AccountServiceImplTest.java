package com.example.fundtransfer.service;

import com.example.fundtransfer.dto.AccountDTO;
import com.example.fundtransfer.entity.Account;
import com.example.fundtransfer.exception.AccountNotFoundException;
import com.example.fundtransfer.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    private Long customerId;
    private Account account1;
    private Account account2;
    private List<Account> accountsList;

    @BeforeEach
    void setUp() {
        customerId = 123L;
        
        account1 = new Account();
        account1.setId(1L);
        account1.setCustomerId(customerId);
        account1.setAccountNumber("ACC001");
        account1.setBalance(new BigDecimal("1000.00"));
        account1.setCreatedAt(LocalDateTime.now());
        account1.setVersion(0L);

        account2 = new Account();
        account2.setId(2L);
        account2.setCustomerId(customerId);
        account2.setAccountNumber("ACC002");
        account2.setBalance(new BigDecimal("2500.50"));
        account2.setCreatedAt(LocalDateTime.now());
        account2.setVersion(0L);

        accountsList = Arrays.asList(account1, account2);
    }

    @Test
    void getAccountsForUser_withAccounts_returnsDTOs() {
        // Arrange
        when(accountRepository.findByCustomerId(customerId)).thenReturn(accountsList);

        // Act
        List<AccountDTO> result = accountService.getAccountsForUser(customerId);

        // Assert
        assertEquals(2, result.size());
        
        AccountDTO dto1 = result.get(0);
        assertEquals(1L, dto1.getId());
        assertEquals(customerId, dto1.getCustomerId());
        assertEquals("ACC001", dto1.getAccountNumber());
        assertEquals(new BigDecimal("1000.00"), dto1.getBalance());
        assertNull(dto1.getCreatedAt()); // Not mapped by service

        AccountDTO dto2 = result.get(1);
        assertEquals(2L, dto2.getId());
        assertEquals(customerId, dto2.getCustomerId());
        assertEquals("ACC002", dto2.getAccountNumber());
        assertEquals(new BigDecimal("2500.50"), dto2.getBalance());
        assertNull(dto2.getCreatedAt()); // Not mapped by service

        verify(accountRepository, times(1)).findByCustomerId(customerId);
    }

    @Test
    void getAccountsForUser_noAccounts_throwsAccountNotFoundException() {
        // Arrange
        when(accountRepository.findByCustomerId(customerId)).thenReturn(Collections.emptyList());

        // Act & Assert
        AccountNotFoundException exception = assertThrows(
            AccountNotFoundException.class,
            () -> accountService.getAccountsForUser(customerId)
        );

        assertEquals("No accounts found for customer: " + customerId, exception.getMessage());
        verify(accountRepository, times(1)).findByCustomerId(customerId);
    }

    @Test
    void getAccountsForUser_singleAccount_returnsSingleDTO() {
        // Arrange
        List<Account> singleAccount = Arrays.asList(account1);
        when(accountRepository.findByCustomerId(customerId)).thenReturn(singleAccount);

        // Act
        List<AccountDTO> result = accountService.getAccountsForUser(customerId);

        // Assert
        assertEquals(1, result.size());
        AccountDTO dto = result.get(0);
        assertEquals(1L, dto.getId());
        assertEquals("ACC001", dto.getAccountNumber());
        assertEquals(new BigDecimal("1000.00"), dto.getBalance());
        assertEquals(customerId, dto.getCustomerId());
        assertNull(dto.getCreatedAt());

        verify(accountRepository, times(1)).findByCustomerId(customerId);
    }

    @Test
    void getAccountsForUser_verifyRepositoryInteraction() {
        // Arrange
        when(accountRepository.findByCustomerId(customerId)).thenReturn(accountsList);

        // Act
        accountService.getAccountsForUser(customerId);

        // Assert
        verify(accountRepository, times(1)).findByCustomerId(customerId);
        verifyNoMoreInteractions(accountRepository);
    }
}