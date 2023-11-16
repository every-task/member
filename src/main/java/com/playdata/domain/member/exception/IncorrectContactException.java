package com.playdata.domain.member.exception;

public class IncorrectContactException extends RuntimeException{
    String data;
    public IncorrectContactException(String message,String data) {
        super(message);
        this.data = data;
    }
}
