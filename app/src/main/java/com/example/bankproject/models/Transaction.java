package com.example.bankproject.models;

import java.math.BigDecimal;

public class Transaction {
    private Long id;
    private Long sourceAccountId;
    private Long destinationAccountId;
    private BigDecimal amount;
    private String status;
    private String transactionDate;

    public Long getId() { return id; }
    public Long getSourceAccountId() { return sourceAccountId; }
    public Long getDestinationAccountId() { return destinationAccountId; }
    public BigDecimal getAmount() { return amount; }
    public String getStatus() { return status; }
    public String getTransactionDate() { return transactionDate; }
}