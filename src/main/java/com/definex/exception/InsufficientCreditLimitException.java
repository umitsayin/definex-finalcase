package com.definex.exception;

public class InsufficientCreditLimitException extends RuntimeException{
    public InsufficientCreditLimitException(String message){
        super(message);
    }
}
