package com.example.housing_service.presention.dataTransferObject.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    @JsonIgnore
    private HttpStatus httpStatus = HttpStatus.OK;
    @JsonIgnore
    private HttpHeaders headers;

    private int code = 200;

    private Boolean success = true;

    private String message;

    private T data;

    private Map<String, Object> errors;

    private Date timestamp = new Date();

    public static <T> ApiResponse<T> build(){
        return new ApiResponse<>();
    }

    @PostConstruct
    private void init(){
        httpStatus = HttpStatus.OK;
        code = httpStatus.value();
        errors = new HashMap<String, Object>();
    }

    public ApiResponse<T> withSuccess(boolean success){
        this.success = success;
        return this;
    }

    public ApiResponse<T> withHttpStatus(HttpStatus status){
        this.httpStatus = status;
        this.code = status.value();
        return this;
    }

    public ApiResponse<T> withCode(int code){
        this.code = code;
        return this;
    }

    public ApiResponse<T> withData(T data){
        this.data = data;
        return this;
    }

    public ApiResponse<T> withHttpHeaders(HttpHeaders headers){
        this.headers = headers;
        return this;
    }

    public ApiResponse<T> withMessage(String message){
        this.message = message;
        return this;
    }

    public ApiResponse<T> withErrors(Map<String, Object> errors){
        this.errors = errors;
        return this;
    }

    public ResponseEntity<?> toEntity(){
        return new ResponseEntity<>(this, headers, httpStatus);
    }
}
