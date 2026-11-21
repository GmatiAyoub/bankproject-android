package com.example.bankproject.models;

import java.math.BigDecimal;

public class TransferRequest {
    private Long sourceAccountId;
    private Long destinationAccountId;
    private BigDecimal amount;

    public TransferRequest(Long sourceAccountId, Long destinationAccountId, BigDecimal amount) {
        this.sourceAccountId = sourceAccountId;
        this.destinationAccountId = destinationAccountId;
        this.amount = amount;
    }

    // Getters
    public Long getSourceAccountId() { return sourceAccountId; }
    public Long getDestinationAccountId() { return destinationAccountId; }
    public BigDecimal getAmount() { return amount; }
}