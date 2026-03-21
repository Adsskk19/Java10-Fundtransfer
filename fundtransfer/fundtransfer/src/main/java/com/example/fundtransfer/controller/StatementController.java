package com.example.fundtransfer.controller;

import com.example.fundtransfer.dto.StatementResponseDTO;
import com.example.fundtransfer.service.StatementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@Tag(name = "Statement APIs", description = "APIs for fetching account statements")
public class StatementController {

    private final StatementService statementService;

    public StatementController(StatementService statementService) {
        this.statementService = statementService;
    }

    @GetMapping("/{accountId}/statements")
    @Operation(
            summary = "Get Account Statement",
            description = "Fetch all transactions for a given account ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Statement fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public List<StatementResponseDTO> getStatements(
            @Parameter(description = "Account ID", required = true)
            @PathVariable Long accountId) {

        return statementService.getStatements(accountId);
    }
}