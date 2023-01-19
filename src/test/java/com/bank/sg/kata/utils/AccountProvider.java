package com.bank.sg.kata.utils;

import com.bank.sg.kata.domain.TransactionType;
import com.bank.sg.kata.domain.model.Account;
import com.bank.sg.kata.domain.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class AccountProvider {

    final static String  IBAN ="FR1420041010050500013M02606";
    private static final double INITIAL_BALANCE = 100.00;

    public static Account getCreatedAccount() {
        return new Account(IBAN);
    }
    public static void initAccount(Account account ){
        BigDecimal amount= BigDecimal.valueOf(100);
        Transaction transaction = Transaction.of( amount, account.getBalance(), TransactionType.DEPOSIT);
        account.deposit(amount);
        account.addTransaction(transaction);
    }
    public static void addTransactionsBetweenDates(Account account , LocalDateTime startDate){
        Transaction transaction= new Transaction( startDate.minusMonths(1), BigDecimal.valueOf(10),BigDecimal.valueOf(1000), TransactionType.DEPOSIT);
        account.addTransaction(transaction);

        transaction=new Transaction( startDate.minusDays(6), BigDecimal.valueOf(20),BigDecimal.valueOf(1010), TransactionType.DEPOSIT);
        account.addTransaction(transaction);

        transaction = new Transaction( startDate.minusDays(5), BigDecimal.valueOf(40),BigDecimal.valueOf(1030), TransactionType.WITHDRAWAL);
        account.addTransaction(transaction);


        transaction =new Transaction( startDate.minusDays(4), BigDecimal.valueOf(50),BigDecimal.valueOf(990), TransactionType.WITHDRAWAL);
        account.addTransaction(transaction);


        transaction =new Transaction( startDate.minusDays(1), BigDecimal.valueOf(10),BigDecimal.valueOf(940), TransactionType.DEPOSIT);
        account.addTransaction(transaction);

    }
}
