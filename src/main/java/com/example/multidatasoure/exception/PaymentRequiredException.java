package com.example.multidatasoure.exception;

public class PaymentRequiredException extends CustomException {
    public PaymentRequiredException(String message, Object... args) {
        super(message, 0, args);
    }
}
