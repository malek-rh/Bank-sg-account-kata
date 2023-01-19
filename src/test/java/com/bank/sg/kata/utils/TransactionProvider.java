//package com.bank.sg.kata.utils;
//
//import kata.bank.domain.TransactionType;
//import kata.bank.domain.model.Transaction;
//import kata.bank.domain.repository.TransactionRepository;
//import kata.bank.infrastructure.utils.IdGenerator;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//
//
//public class TransactionProvider {
//    final static String  FIRST_IBAN ="FR1420041010050500013M02606";
//    final static String  SECOND_IBAN ="FR1420041010050500013213560";
//
//    public static void addTransactions(TransactionRepository transactionRepository){
//        transactionRepository.addTransaction(Transaction.of(FIRST_IBAN, BigDecimal.valueOf(10),BigDecimal.valueOf(1000), TransactionType.DEPOSIT));
//        transactionRepository.addTransaction(Transaction.of(FIRST_IBAN, BigDecimal.valueOf(10),BigDecimal.valueOf(1000), TransactionType.WITHDRAWAL));
//        transactionRepository.addTransaction(Transaction.of(SECOND_IBAN, BigDecimal.valueOf(10),BigDecimal.valueOf(1000), TransactionType.WITHDRAWAL));
//        transactionRepository.addTransaction(Transaction.of(SECOND_IBAN, BigDecimal.valueOf(10),BigDecimal.valueOf(1000), TransactionType.DEPOSIT));
//    }
//
//
//    public static void addTransactionsBetweenDates(TransactionRepository transactionRepository, LocalDate startDate, LocalDate endDate){
//        transactionRepository.addTransaction(new Transaction(IdGenerator.generateId(),FIRST_IBAN, startDate.plusDays(1), BigDecimal.valueOf(10),BigDecimal.valueOf(1000), TransactionType.DEPOSIT));
//        transactionRepository.addTransaction(new Transaction(IdGenerator.generateId(),FIRST_IBAN, startDate.plusDays(2), BigDecimal.valueOf(20),BigDecimal.valueOf(1010), TransactionType.DEPOSIT));
//        transactionRepository.addTransaction(new Transaction(IdGenerator.generateId(),FIRST_IBAN, startDate.plusDays(3), BigDecimal.valueOf(40),BigDecimal.valueOf(1030), TransactionType.WITHDRAWAL));
//        transactionRepository.addTransaction(new Transaction(IdGenerator.generateId(),FIRST_IBAN, startDate.plusDays(4), BigDecimal.valueOf(50),BigDecimal.valueOf(990), TransactionType.WITHDRAWAL));
//        transactionRepository.addTransaction(new Transaction(IdGenerator.generateId(),FIRST_IBAN, startDate.plusMonths(1), BigDecimal.valueOf(10),BigDecimal.valueOf(940), TransactionType.DEPOSIT));
//        transactionRepository.addTransaction(new Transaction(IdGenerator.generateId(),FIRST_IBAN, startDate.plusMonths(2), BigDecimal.valueOf(20),BigDecimal.valueOf(950), TransactionType.DEPOSIT));
//        transactionRepository.addTransaction(new Transaction(IdGenerator.generateId(),FIRST_IBAN, startDate.plusMonths(3), BigDecimal.valueOf(40),BigDecimal.valueOf(910), TransactionType.WITHDRAWAL));
//        transactionRepository.addTransaction(new Transaction(IdGenerator.generateId(),FIRST_IBAN, startDate.plusMonths(4), BigDecimal.valueOf(50),BigDecimal.valueOf(860), TransactionType.WITHDRAWAL));
//    }
//}
