package com.bank.sg.kata.domain.model;

import com.bank.sg.kata.domain.exception.InsufficientFundsException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private final String iban;
    private BigDecimal balance;
    private List<Transaction> transactions;

    public Account(String iban) {
        this.iban = iban;
        this.balance = BigDecimal.ZERO;
        this.transactions = new ArrayList<>();
    }

    public void deposit(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        if (balance.compareTo(amount) < 0) {
            throw new InsufficientFundsException("Funds insufficient to process this operation !");
        }
        this.balance = this.balance.subtract(amount);
    }

    public String getIban() {
        return iban;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }
}
