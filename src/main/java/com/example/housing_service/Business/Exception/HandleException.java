package com.example.housing_service.Business.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandleException {
    @ExceptionHandler({ HousingException.class})  // Có thể bắt nhiều loại exception
    public ResponseEntity<ErrorModel> handleExceptionA(HousingException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getErrorModel());
    }
}
