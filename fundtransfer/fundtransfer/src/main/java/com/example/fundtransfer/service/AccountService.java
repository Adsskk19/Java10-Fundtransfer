package com.example.fundtransfer.service;

import com.example.fundtransfer.dto.AccountDTO;

import java.util.List;

public interface AccountService {

    List<AccountDTO> getAccountsForUser(Long customerId);

}
