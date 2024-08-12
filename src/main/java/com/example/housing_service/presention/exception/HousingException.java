package com.example.housing_service.presention.exception;

import com.example.housing_service.presention.configration.ApplicationContextHolder;
import lombok.Data;

@Data
public class HousingException extends RuntimeException{
    private ErrorModel errorModel;

    public HousingException(String errorCode) {
        ErrorModel errorException = new ErrorModel();
        errorException.setErrorCode(errorCode);
        String errorMessage = "";
        MappingException mappingException = ApplicationContextHolder.getApplicationContext().getBean(MappingException.class);
        errorMessage = mappingException.getErrorMessage(errorCode);
        errorException.setErrorMessage(errorMessage);
        this.errorModel = errorException;
    }
}
