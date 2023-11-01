package com.playdata.domain.member.exception;

public class ExistEmailException extends RuntimeException{
    public ExistEmailException(String message) {
        super(message);
    }
}
