package com.playdata.domain.member.exception;

public class LoginFailException extends RuntimeException{


    public LoginFailException(String message) {
        super(message);
    }
}
