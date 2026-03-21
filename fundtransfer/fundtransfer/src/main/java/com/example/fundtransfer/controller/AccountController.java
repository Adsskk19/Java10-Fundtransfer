package com.example.fundtransfer.controller;

import com.example.fundtransfer.entity.Account;
import com.example.fundtransfer.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/{customerId}")
    public List<Account> getAccounts(@PathVariable Long customerId) {
        return accountService.getAccountsForUser(customerId);
    }
}
