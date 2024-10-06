package com.openlab.tables.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TablesObjectIllegalArgumentException extends RuntimeException{
    private String message;

    public TablesObjectIllegalArgumentException(String message) {
        this.message = message;
    }

    public TablesObjectIllegalArgumentException(String message, String message1) {
        super(message);
        this.message = message1;
    }

    public TablesObjectIllegalArgumentException(String message, Throwable cause, String message1) {
        super(message, cause);
        this.message = message1;
    }


}
