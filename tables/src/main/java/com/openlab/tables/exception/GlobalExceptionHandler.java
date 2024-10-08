package com.openlab.tables.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TablesAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handlerTablesAlreadyExistException(TablesAlreadyExistsException ex){
         ErrorResponse errorResponse = errorDetails(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


    @ExceptionHandler(TablesNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTablesNotFoundException(TablesNotFoundException ex){
         ErrorResponse errorResponse = errorDetails(ex);
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


    @ExceptionHandler(TablesObjectIllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleTablesObjectIllegalArgument( TablesObjectIllegalArgumentException ex){
        ErrorResponse errorResponse = errorDetails(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    public ErrorResponse errorDetails(Exception ex){
        return ErrorResponse.builder()
                .message(ex.getMessage())
                .build();

    }

}
