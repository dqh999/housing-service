package com.example.housing_service.presention.exception;

import lombok.Data;

@Data
public class ErrorModel {
    private String errorCode;
    private String errorMessage;
}
