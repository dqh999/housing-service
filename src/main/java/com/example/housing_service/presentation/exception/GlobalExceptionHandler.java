package com.example.housing_service.presentation.exception;
import com.example.housing_service.presentation.dataTransferObject.response.ApiResponse;
import com.example.housing_service.presentation.dataTransferObject.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<?>> handleBusinessException(BusinessException e) {
        ResponseCode code = e.getResponseCode();
        ApiResponse<?> errorResponse;

        if (code != null) {
            Map<String, Object> errors = new HashMap<>();
            errors.put("type", code.getType());

            errorResponse = ApiResponse.<Void>build()
                    .withCode(code.getCode())
                    .withErrors(errors)
                    .withMessage(e.getMessage())
                    .withSuccess(false);
            return ResponseEntity
                    .status(code.getCode())
                    .body(errorResponse);
        }
        Map<String, Object> errors = new HashMap<>();
        errors.put("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

        errorResponse = ApiResponse.<Void>build()
                .withCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .withErrors(errors)
                .withMessage(e.getMessage())
                .withSuccess(false);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ApiResponse<?>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ApiResponse<Map<String, String>> errorResponse = ApiResponse.<Map<String, String>>build()
                .withCode(HttpStatus.BAD_REQUEST.value())
                .withErrors(errors)
                .withMessage("Validation failed")
                .withSuccess(false);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiResponse<?>> handleThrowable(Throwable e) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

        ApiResponse<?> errorResponse = ApiResponse.<Void>build()
                .withHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .withErrors(errors)
                .withMessage(e.getMessage())
                .withSuccess(false);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }
}
