package com.example.fundtransfer.service;

import com.example.fundtransfer.dto.StatementResponseDTO;

import java.util.List;

public interface StatementService {

    public List<StatementResponseDTO> getStatements(Long accountId);
}
