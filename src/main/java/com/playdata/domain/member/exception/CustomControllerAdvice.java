package com.playdata.domain.member.exception;

import com.playdata.domain.member.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CustomControllerAdvice {

    // Slf4j 로 각각 의 에러를 로그로 남겨두는 것 생각해보아야함.
    @ExceptionHandler(LoginFailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse loginFailExceptionHandler(LoginFailException e){
        return new ErrorResponse(e.getMessage());
    }
    @ExceptionHandler(ExistEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse existEmailExceptionHandler(ExistEmailException e){
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(FirebaseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse firebaseExceptionHandler(FirebaseException e){
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(KafkaCommandException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse kafkaCommandExceptionHandler(KafkaCommandException e){
        return new ErrorResponse(e.getMessage());
    }

}
