package com.bank.sg.kata.domain.repository;

import com.bank.sg.kata.domain.model.Account;
import com.bank.sg.kata.domain.model.Transaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    Account save(Account any);

    Optional<Account> findByIban(String IBAN);

    Account updateAccount(Account account);

    List<Transaction> getStatementsBetweenDates(String iban, LocalDateTime startDate, LocalDateTime endDate);
}
