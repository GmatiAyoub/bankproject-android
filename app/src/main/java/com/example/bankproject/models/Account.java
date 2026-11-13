package com.example.bankproject.models;

import java.math.BigDecimal;

public class Account {
    private Long id;
    private String accountNumber;
    private Long customerId;
    private BigDecimal balance;
    private String status;

    // Getters (pour l'affichage)
    public Long getId() { return id; }
    public String getAccountNumber() { return accountNumber; }
    public Long getCustomerId() { return customerId; }
    public BigDecimal getBalance() { return balance; }
    public String getStatus() { return status; }

    // Setters (pour la création)
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
}