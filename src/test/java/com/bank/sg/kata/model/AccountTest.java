package com.bank.sg.kata.model;


import com.bank.sg.kata.domain.exception.InsufficientFundsException;
import com.bank.sg.kata.domain.model.Account;
import com.bank.sg.kata.service.AccountService;
import com.bank.sg.kata.utils.AccountProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {

    private AccountService accountService;

    @BeforeEach
    public void setUp() {
        accountService = new AccountService();
    }

    @Test
    public void shouldUpdateBalanceAfterDeposit() {
        //given
        final Account account = AccountProvider.getCreatedAccount();
        //when
        account.deposit(BigDecimal.valueOf(100));
        //then
        assertTrue(BigDecimal.valueOf(100).compareTo(account.getBalance()) == 0);
    }

    @Test
    public void shouldUpdateBalanceAfterWithdrawal() {
        //given
        final Account account = AccountProvider.getCreatedAccount();
        account.deposit(BigDecimal.valueOf(50));
        //when
        account.withdraw(BigDecimal.valueOf(50));
        //then
        assertTrue(BigDecimal.valueOf(0).compareTo(account.getBalance()) == 0);
    }

    @Test
    public void shouldNotWithdrawWhenInsufficientFunds() {
        final Account account = AccountProvider.getCreatedAccount();
        //then
        assertThrows(InsufficientFundsException.class, () -> account.withdraw(BigDecimal.valueOf(200)));
    }

    @Test
    public void showStatementsBetweenTwoDates() {
        final Account account = AccountProvider.getCreatedAccount();
        AccountProvider.addTransactionsBetweenDates(account, LocalDateTime.now());
        assertEquals(account.getTransactions().size(), 5);
    }
}


