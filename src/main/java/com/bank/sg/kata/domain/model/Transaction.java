package com.bank.sg.kata.domain.model;

import com.bank.sg.kata.domain.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public final class Transaction {

    private LocalDateTime date;
    private BigDecimal amount;
    private BigDecimal balance;
    private TransactionType transactionType;


    public Transaction(LocalDateTime date, BigDecimal amount, BigDecimal balance, TransactionType transactionType) {
        this.date = date;
        this.amount = amount;
        this.balance = balance;
        this.transactionType = transactionType;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }



    public static Transaction of(  BigDecimal amount, BigDecimal balance, TransactionType transactionType){
        return new Transaction(LocalDateTime.now(),amount, balance, transactionType);
    }

}
