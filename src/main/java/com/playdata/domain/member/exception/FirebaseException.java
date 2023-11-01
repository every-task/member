package com.playdata.domain.member.exception;

public class FirebaseException extends RuntimeException{

    public FirebaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
