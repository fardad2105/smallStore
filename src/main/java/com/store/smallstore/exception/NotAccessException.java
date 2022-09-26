package com.store.smallstore.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class NotAccessException extends RuntimeException {

    private final HttpStatus httpStatus;
    private String message;

    public NotAccessException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
