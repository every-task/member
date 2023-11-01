package com.playdata.domain.member.exception;

public class KafkaCommandException extends RuntimeException{

    public KafkaCommandException(String message) {
        super(message);
    }
}
