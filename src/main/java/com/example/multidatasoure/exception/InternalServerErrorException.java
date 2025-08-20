package com.example.multidatasoure.exception;

public class InternalServerErrorException extends CustomException {
    public InternalServerErrorException(String message, Throwable cause) {
        super(message, 0, cause);
    }

    public InternalServerErrorException(String message) {
        super(message, 0);
    }
}
