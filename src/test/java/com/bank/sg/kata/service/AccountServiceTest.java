package com.bank.sg.kata.service;


import com.bank.sg.kata.domain.exception.AccountNotExistException;
import com.bank.sg.kata.domain.exception.InsufficientFundsException;
import com.bank.sg.kata.domain.model.Account;
import com.bank.sg.kata.domain.repository.AccountRepository;
import com.bank.sg.kata.utils.AccountProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createAccount_thenSaveAccount() {
        final Account account = AccountProvider.getCreatedAccount();
        when(accountRepository.save(account)).thenReturn(account);
        final Account savedAccount = accountService.createAccount(account);
        verify(accountRepository).save(any(Account.class));
        assertNotNull(savedAccount);
    }


    @Test
    public void searchAccountByIBAN_theReturnAccount() {
        final Account account = AccountProvider.getCreatedAccount();
        when(accountRepository.save(account)).thenReturn(account);
        when(accountRepository.findByIban(account.getIban())).thenReturn(Optional.of(account));
        Account savedAccount = accountService.createAccount(account);
        assertEquals(savedAccount, accountService.getAccount(account.getIban()));
    }

    @Test
    public void searchAccountByIBAN_theReturnException() {
        final Account account = AccountProvider.getCreatedAccount();
        when(accountRepository.findByIban(account.getIban())).thenReturn(Optional.empty());
        verify(accountRepository, times(0)).findByIban(any());
        assertThrows(AccountNotExistException.class, () -> accountService.getAccount(account.getIban()));

    }

    // Test Case Update account After Deposit
    @Test
    public void should_depositAmountByIBAN() {
        //given
        final Account account = AccountProvider.getCreatedAccount();

        //when
        when(accountRepository.save(account)).thenReturn(account);
        when(accountRepository.findByIban(account.getIban())).thenReturn(Optional.of(account));
        accountService.deposit(account.getIban(), BigDecimal.valueOf(100));

        //then
        verify(accountRepository, times(1)).findByIban(account.getIban());
        verify(accountRepository, times(1)).updateAccount(any());
        assertEquals(account.getBalance(), BigDecimal.valueOf(100));
        assertEquals(account.getTransactions().size(), 1);
        assertEquals(account.getTransactions().get(0).getAmount(), BigDecimal.valueOf(100));
    }


    // Test Case Update account After Withdraw
    @Test
    public void should_WithdrawAmountByIBAN() {
        //given
        final Account account = AccountProvider.getCreatedAccount();
        //init solde by transaction (100)
        AccountProvider.initAccount(account);
        //when
        when(accountRepository.save(account)).thenReturn(account);
        when(accountRepository.findByIban(account.getIban())).thenReturn(Optional.of(account));

        accountService.withDraw(account.getIban(), BigDecimal.valueOf(100));
        //then
        verify(accountRepository, times(1)).findByIban(account.getIban());
        verify(accountRepository, times(1)).updateAccount(any());
        assertEquals(account.getBalance(), BigDecimal.valueOf(0));
        assertEquals(account.getTransactions().size(), 2);
    }

    @Test
    public void WithdrawAmountByIBAN_theReturnException() {
        //given
        final Account account = AccountProvider.getCreatedAccount();
        //init solde by transaction (100)
        AccountProvider.initAccount(account);
        //when
        when(accountRepository.save(account)).thenReturn(account);
        when(accountRepository.findByIban(account.getIban())).thenReturn(Optional.of(account));

        assertThrows(InsufficientFundsException.class, () -> account.withdraw(BigDecimal.valueOf(200)));
        //then
        verify(accountRepository, times(0)).updateAccount(any());
        assertEquals(account.getBalance(), BigDecimal.valueOf(100));
        assertEquals(account.getTransactions().size(), 1);

    }

    @Test
    public void should_getStatementsBetweenDates() {
        //given
        final Account account = AccountProvider.getCreatedAccount();
        AccountProvider.addTransactionsBetweenDates(account, LocalDateTime.now());
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(10);
        //when
        when(accountRepository.findByIban(account.getIban())).thenReturn(Optional.of(account));

        accountService.getStatementsBetweenDates(account.getIban(), startDate, endDate);

        //then
        verify(accountRepository, times(1)).getStatementsBetweenDates(account.getIban(), startDate, endDate);


    }
}
