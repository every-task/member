package com.playdata.domain.member.exception;

import com.playdata.domain.member.response.ErrorResponse;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomControllerAdvice {
    @ExceptionHandler(LoginFailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse loginFailExceptionHandler(LoginFailException e){
        return new ErrorResponse(e.getMessage(), e.getCause());
    }
    @ExceptionHandler(ExistEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse existEmailExceptionHandler(ExistEmailException e){
        return new ErrorResponse(e.getMessage(), e.getCause());
    }

    @ExceptionHandler(FirebaseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse firebaseExceptionHandler(FirebaseException e){
        return new ErrorResponse(e.getMessage(), e.getCause());
    }

    @ExceptionHandler(KafkaCommandException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse kafkaCommandExceptionHandler(KafkaCommandException e){
        return new ErrorResponse(e.getMessage(), e.getCause());
    }

}
