package com.bank.sg.kata.infrastructure.repository;

import com.bank.sg.kata.domain.exception.AccountNotExistException;
import com.bank.sg.kata.domain.model.Account;
import com.bank.sg.kata.domain.model.Transaction;
import com.bank.sg.kata.domain.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Service
public class AccountRepositoryInMemory implements AccountRepository {
    private Map<String, Account> accountsByIban = new HashMap<>();

    @Override
    public Account save(Account account) {
        requireNonNull(account);
        accountsByIban.put(account.getIban(), account);
        return accountsByIban.get(account.getIban());
    }

    @Override
    public Optional<Account> findByIban(String IBAN) {
        requireNonNull(IBAN);
        return Optional.ofNullable(accountsByIban.get(IBAN));
    }

    @Override
    public Account updateAccount(Account account) {
        return accountsByIban.computeIfPresent(account.getIban(), (a, b) -> account);
    }

    @Override
    public List<Transaction> getStatementsBetweenDates(String iban, LocalDateTime startDate, LocalDateTime endDate) {
        return findByIban(iban).orElseThrow(() ->
                        new AccountNotExistException("There is no account related to this IBAN"))
                .getTransactions().stream()
                .filter(transaction -> datePredicate(startDate, endDate, transaction))
                .sorted(Comparator.comparing(Transaction::getDate).reversed())
                .collect(Collectors.toList());
    }

    private boolean datePredicate(LocalDateTime startDate, LocalDateTime endDate, Transaction transaction) {
        return transaction.getDate().isBefore(endDate) && transaction.getDate().isAfter(startDate);
    }
}
