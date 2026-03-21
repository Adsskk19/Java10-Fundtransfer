package com.example.fundtransfer.service;

import com.example.fundtransfer.entity.Account;
import com.example.fundtransfer.dto.AccountDTO;
import com.example.fundtransfer.exception.AccountNotFoundException;
import com.example.fundtransfer.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<AccountDTO> getAccountsForUser(Long customerId) {
        List<Account> accounts = accountRepository.findByCustomerId(customerId);
        List<AccountDTO> dtoList = accounts.stream()
                .map(account -> {
                    AccountDTO dto = new AccountDTO();
                    dto.setId(account.getId());
                    dto.setAccountNumber(account.getAccountNumber());
                    dto.setBalance(account.getBalance());
                    dto.setCustomerId(account.getCustomerId());
                    return dto;
                })
                .toList();

        if (dtoList.isEmpty()) {
            throw new AccountNotFoundException("No accounts found for customer: " + customerId);
        }

        return dtoList;
    }
}
