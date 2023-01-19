package com.bank.sg.kata.domain.exception;

public class InsufficientFundsException extends IllegalArgumentException {
    public InsufficientFundsException(String message){
        super(message);
    }
}
