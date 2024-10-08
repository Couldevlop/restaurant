package com.openlab.menu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MenuObjectIllegalArgumentException extends RuntimeException{
    private String message;

    public MenuObjectIllegalArgumentException(String message) {
        this.message = message;
    }

    public MenuObjectIllegalArgumentException(String message, String message1) {
        super(message);
        this.message = message1;
    }

    public MenuObjectIllegalArgumentException(String message, Throwable cause, String message1) {
        super(message, cause);
        this.message = message1;
    }
}
