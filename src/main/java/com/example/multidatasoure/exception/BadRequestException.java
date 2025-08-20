package com.example.multidatasoure.exception;

public class BadRequestException extends CustomException {
    public BadRequestException(String message) {
        super(message, 0);
    }

    public BadRequestException(String message, Object... args) {
        super(message, 0, args);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, 0, cause);
    }
}
