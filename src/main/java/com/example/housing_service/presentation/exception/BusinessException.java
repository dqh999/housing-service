package com.example.housing_service.presentation.exception;


import com.example.housing_service.presentation.dataTransferObject.response.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessException extends RuntimeException {

    ResponseCode responseCode;

    public BusinessException(ResponseCode responseCode, String message) {
        super(message);
        this.responseCode = responseCode;
    }
}
