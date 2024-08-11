package com.example.housing_service.present.Exception;

import lombok.Data;

@Data
public class ErrorModel {
    private String errorCode;
    private String errorMessage;
}
