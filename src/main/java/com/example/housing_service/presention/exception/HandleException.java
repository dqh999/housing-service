package com.example.housing_service.presention.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandleException {
    @ExceptionHandler({ HousingException.class})
    public ResponseEntity<ErrorModel> handleExceptionA(HousingException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getErrorModel());
    }

}
