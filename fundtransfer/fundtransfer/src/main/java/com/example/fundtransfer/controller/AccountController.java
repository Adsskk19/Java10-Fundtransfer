package com.example.fundtransfer.controller;

import com.example.fundtransfer.dto.AccountDTO;
import com.example.fundtransfer.entity.Account;
import com.example.fundtransfer.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/{customerId}")
    public List<AccountDTO> getAccounts(@PathVariable Long customerId) {
        return accountService.getAccountsForUser(customerId);
    }
}
