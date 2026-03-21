package com.example.fundtransfer.service;



import com.example.fundtransfer.dto.StatementResponseDTO;
import com.example.fundtransfer.entity.Account;
import com.example.fundtransfer.entity.Transaction;
import com.example.fundtransfer.exception.ResourceNotFoundException;
import com.example.fundtransfer.repository.AccountRepository;
import com.example.fundtransfer.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatementServiceImpl implements StatementService{

    @Autowired
    private final TransactionRepository transactionRepository;

    @Autowired
    private final AccountRepository accountRepository;

    public StatementServiceImpl(TransactionRepository transactionRepository,
                                AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<StatementResponseDTO> getStatements(Long accountId) {


        Account account = accountRepository
                .findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        List<Transaction> transactions = transactionRepository.findByAccountId(account.getId());


        return transactions.stream()
                .map(txn -> mapToDTO(txn))
                .collect(Collectors.toList());

    }

    private StatementResponseDTO mapToDTO(Transaction txn) {

        StatementResponseDTO dto = new StatementResponseDTO();

        dto.setTransactionId(txn.getId());
        dto.setTransactionDate(txn.getCreatedAt());
        dto.setAmount(txn.getAmount());
        dto.setDescription(txn.getComment());

        return dto;
    }
}
