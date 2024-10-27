package com.openlab.menu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PlatNotFoundException extends RuntimeException{
    private  String message;

    public PlatNotFoundException(String message) {
        this.message = message;
    }

    public PlatNotFoundException(String message, String message1) {
        super(message);
        this.message = message1;
    }

    public PlatNotFoundException(String message, Throwable cause, String message1) {
        super(message, cause);
        this.message = message1;
    }
}
