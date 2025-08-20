package com.example.multidatasoure.exception;

public class ConflictException extends CustomException {

    public ConflictException(String message) {
        super(message, 0);
    }

    public ConflictException(String message, Object... args) {
        super(message, 0, args);
    }
}