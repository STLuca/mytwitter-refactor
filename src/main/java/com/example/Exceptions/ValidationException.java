package com.example.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationException extends RuntimeException {

    public ValidationException(
            String field,
            Object value,
            String validationRule
    ) {
        super("Invalid value: " + value + " for field: " + field + ". " + validationRule);
    }
}
