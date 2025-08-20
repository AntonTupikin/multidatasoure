package com.example.multidatasoure.exception.handler;

import com.example.multidatasoure.controller.response.ExceptionResponse;
import com.example.multidatasoure.exception.BadRequestException;
import com.example.multidatasoure.exception.ConflictException;
import com.example.multidatasoure.exception.CustomException;
import com.example.multidatasoure.exception.ForbiddenException;
import com.example.multidatasoure.exception.InternalServerErrorException;
import com.example.multidatasoure.exception.NotFoundException;
import com.example.multidatasoure.exception.PaymentRequiredException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Locale;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class CustomExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleException(Exception e, Locale locale) {
        return getExceptionResponse(e, "message.exception.unknown", locale);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handle(HttpMessageNotReadableException e, Locale locale) {
        return getExceptionResponse(e, "message.exception.http-message-not-readable", locale);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handle(BadRequestException e, Locale locale) {
        return getExceptionResponse(e, locale);
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handle(ConflictException e, Locale locale) {
        return getExceptionResponse(e, locale);
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse handle(ForbiddenException e, Locale locale) {
        return getExceptionResponse(e, locale);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handle(InternalServerErrorException e, Locale locale) {
        return getExceptionResponse(e, locale);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handle(NotFoundException e, Locale locale) {
        return getExceptionResponse(e, locale);
    }

    @ExceptionHandler(PaymentRequiredException.class)
    @ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
    public ExceptionResponse handle(PaymentRequiredException e, Locale locale) {
        return getExceptionResponse(e, locale);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handle(BindException e, Locale locale) {
        return getExceptionResponse(e, "message.exception.bind", locale, e.getFieldErrors().stream()
                .map(FieldError::getField).collect(Collectors.joining(", ")));
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionResponse handle(BadCredentialsException e, Locale locale) {
        return getExceptionResponse(e, "message.exception.bad-credentials", locale);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    //TODO: Переделать при возникновении
    public ExceptionResponse handle(ConstraintViolationException e) {
        log.error("Handle exception", e);
        String message = e.getConstraintViolations().stream().map(constraintViolation -> String.format("%s: %s", constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage())).collect(Collectors.joining("; "));
        return new ExceptionResponse(message);
    }

    @ExceptionHandler(PropertyReferenceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handle(PropertyReferenceException e, Locale locale) {
        return getExceptionResponse(e, "message.exception.property", locale, e.getPropertyName());
    }

    @ExceptionHandler(LockedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse handle(LockedException e, Locale locale) {
        return getExceptionResponse(e, "message.exception.locked", locale);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handle(MethodArgumentTypeMismatchException e, Locale locale) {
        return getExceptionResponse(e, "message.exception.method-argument-type-mismatch", locale, e.getName());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handle(HttpRequestMethodNotSupportedException e, Locale locale) {
        return getExceptionResponse(e, "message.exception.http-request-method-not-supported", locale, e.getMethod());
    }

    private ExceptionResponse getExceptionResponse(Exception e, String placeHolder, Locale locale, Object... args) {
        log.error("Handle exception", e);
        return new ExceptionResponse(getMessage(placeHolder, locale, args));
    }

    private ExceptionResponse getExceptionResponse(CustomException e, Locale locale) {
        String message = getMessage(e.getMessage(), locale, e.getArgs());
        log.error("Handle exception. Message: %s".formatted(message), e);
        return new ExceptionResponse(message);
    }

    private String getMessage(String placeHolder, Locale locale, Object... args) {
        return messageSource.getMessage(placeHolder, args, locale);
    }
}