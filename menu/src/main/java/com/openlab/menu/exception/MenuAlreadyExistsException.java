package com.openlab.menu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MenuAlreadyExistsException extends RuntimeException{
    private String message;

    public MenuAlreadyExistsException(String message) {
        this.message = message;
    }

    public MenuAlreadyExistsException(String message, String message1) {
        super(message);
        this.message = message1;
    }
}
