package com.openlab.menu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MenuNotFoundException extends RuntimeException{
    private String message;

    public MenuNotFoundException(String message) {
        this.message = message;
    }

    public MenuNotFoundException(String message, String message1) {
        super(message);
        this.message = message1;
    }
}
