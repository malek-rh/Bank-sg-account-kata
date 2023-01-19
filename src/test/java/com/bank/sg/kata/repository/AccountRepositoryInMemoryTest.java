package com.bank.sg.kata.repository;


import com.bank.sg.kata.domain.exception.AccountNotExistException;
import com.bank.sg.kata.domain.model.Account;
import com.bank.sg.kata.domain.model.Transaction;
import com.bank.sg.kata.infrastructure.repository.AccountRepositoryInMemory;
import com.bank.sg.kata.utils.AccountProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AccountRepositoryInMemoryTest {
    final static String IBAN = "FR1420041010050500013M02606";
    @InjectMocks
    private AccountRepositoryInMemory transactionRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_saveAccount() {
        final Account account = AccountProvider.getCreatedAccount();
        //init solde by transaction (100)
        AccountProvider.initAccount(account);
        final Account savedAccount = transactionRepository.save(account);
        assertNotNull(savedAccount);
        assertThat(transactionRepository.findByIban(account.getIban()).isPresent());
        assertThat(transactionRepository.findByIban(account.getIban()).get()).isEqualToComparingFieldByFieldRecursively(savedAccount);
    }

    @Test
    public void should_updateAccount() {
        final Account account = AccountProvider.getCreatedAccount();
        //init solde by transaction (100)
        AccountProvider.initAccount(account);
        Account savedAccount = transactionRepository.save(account);
        savedAccount.deposit(BigDecimal.valueOf(100));
        assertNotNull(savedAccount);
        //then
        Account updatedAccount = transactionRepository.save(account);
        assertThat(transactionRepository.findByIban(updatedAccount.getIban()).isPresent());
        assertThat(transactionRepository.findByIban(updatedAccount.getIban()).get()).isEqualToComparingFieldByFieldRecursively(updatedAccount);
    }

    @Test
    public void should_findListOfTransactionsByIbanBetweenDate() {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(2);
        final Account account = AccountProvider.getCreatedAccount();
        //init solde by transaction (100)
        AccountProvider.addTransactionsBetweenDates(account,endDate);
        transactionRepository.save(account);
        //when
        final List<Transaction> transactionList = transactionRepository.getStatementsBetweenDates(IBAN, startDate, endDate);
        assertNotNull(transactionList);
        assertEquals(transactionList.size(), 1);
    }
    @Test
    public void findListOfTransactionsByIbanBetweenDate_theReturnException() {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(2);
        //when
        assertThrows(AccountNotExistException.class, () -> transactionRepository.getStatementsBetweenDates(IBAN, startDate, endDate));

    }
}
