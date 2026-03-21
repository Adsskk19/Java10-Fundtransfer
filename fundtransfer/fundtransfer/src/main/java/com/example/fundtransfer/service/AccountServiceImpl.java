package com.example.fundtransfer.service;

import com.example.fundtransfer.entity.Account;
import com.example.fundtransfer.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<Account> getAccountsForUser(Long customerId) {
        return accountRepository.findByCustomerId(customerId);
    }

    @Override
    public Account getAccount(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }
}
