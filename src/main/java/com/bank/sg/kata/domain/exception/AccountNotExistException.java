package com.bank.sg.kata.domain.exception;

public class AccountNotExistException extends IllegalArgumentException {
    public AccountNotExistException(String message) {
        super(message);
    }
}
