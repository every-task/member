package com.playdata.domain.member.exception;

import com.playdata.domain.member.response.ErrorResponse;
import jakarta.validation.UnexpectedTypeException;
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
    public ErrorResponse handleLoginFailException(LoginFailException exception){
        log.error(exception.getMessage(), exception);;
        return new ErrorResponse(exception.getMessage());
    }
    @ExceptionHandler(ExistEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleExistEmailException(ExistEmailException exception){
        log.error(exception.getMessage(),exception);
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(FirebaseException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleFirebaseException(FirebaseException exception){
        log.error(exception.getMessage(),exception);
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(KafkaCommandException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleKafkaCommandException(KafkaCommandException exception){
        log.error(exception.getMessage(),exception);
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(IncorrectContactException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleIncorrectContactException(IncorrectContactException exception){
        log.error(exception.getMessage(),exception);
        return new ErrorResponse(exception.getMessage());

    }


    @ExceptionHandler(UnexpectedTypeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public  ErrorResponse handleMethodArgumentNotValidException(UnexpectedTypeException exception){
        log.error(exception.getMessage());
        return new ErrorResponse(exception.getLocalizedMessage());
    }



}
