package com.example.fundtransfer.service;

import com.example.fundtransfer.entity.Account;

import java.util.List;

public interface AccountService {

    List<Account> getAccountsForUser(Long customerId);
    Account getAccount(Long id);

}
