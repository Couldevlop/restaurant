package com.openlab.menu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MenuAlreadyExistsException.class)
    public ResponseEntity<?>handleMenuAlreadyExistsException(MenuAlreadyExistsException ex){
        ErrorResponse error = errorDetails(ex);
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


    public ResponseEntity<?> handleMenuObjectIllegalArgumentException(MenuAlreadyExistsException ex){
        ErrorResponse error = errorDetails(ex);
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


    @ExceptionHandler(MenuNotFoundException.class)
    public ResponseEntity<?> handleMenuNotFoundException(MenuNotFoundException ex){
        ErrorResponse error = errorDetails(ex);
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    private ErrorResponse errorDetails( Exception ex){
        return ErrorResponse.builder()
                .message(ex.getMessage())
                .build();
    }

}
