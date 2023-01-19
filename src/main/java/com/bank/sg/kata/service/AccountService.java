package com.bank.sg.kata.service;

import com.bank.sg.kata.domain.TransactionType;
import com.bank.sg.kata.domain.exception.AccountNotExistException;
import com.bank.sg.kata.domain.model.Account;
import com.bank.sg.kata.domain.model.Transaction;
import com.bank.sg.kata.domain.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account getAccount(String iban) {
        return accountRepository.findByIban(iban).orElseThrow(() ->
                new AccountNotExistException("There is no account related to this IBAN"));
    }

    public Account deposit(String iban, BigDecimal amount) {
        Account account = getAccount(iban);
        account.deposit(amount);
        addTransaction(account,amount, TransactionType.DEPOSIT );
        return accountRepository.updateAccount(account);
    }

    public Account withDraw(String iban, BigDecimal amount) {
        Account account = getAccount(iban);
        account.withdraw(amount);
        addTransaction(account,amount, TransactionType.WITHDRAWAL );
        return accountRepository.updateAccount(account);
    }
    public List<Transaction> getStatementsBetweenDates(String iban, LocalDateTime starDate, LocalDateTime endDate) {
        return accountRepository.getStatementsBetweenDates(iban, starDate,endDate);
    }

    public void addTransaction(Account account, BigDecimal amount, TransactionType transactionType) {
        Transaction transaction = Transaction.of( amount, account.getBalance(), transactionType);
        account.addTransaction(transaction);
    }
}
