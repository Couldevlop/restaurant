package com.openlab.tables.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TablesNotFoundException extends RuntimeException{
    public TablesNotFoundException() {
    }

    public TablesNotFoundException(String message) {
        super(message);
    }

    public TablesNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
