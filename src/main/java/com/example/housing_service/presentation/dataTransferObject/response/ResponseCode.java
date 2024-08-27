package com.example.housing_service.presentation.dataTransferObject.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCode {
    SUCCESS("SUCCESS", 200),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", 599),
    BAD_REQUEST("BAD_REQUEST", 400),
    INVALID_PARAMS("INVALID_PARAMS", 400),

    HOUSE_NOT_FOUND("HOUSE_NOT_FOUND", 404),
    ATTACHMENT_SAVE_FAILED("ATTACHMENT_SAVE_FAILED", 500),
    USER_NOT_FOUND("USER_NOT_FOUND", 404),

    HOUSE_UPDATE_FAILED("HOUSE_UPDATE_FAILED", 500),
    HOUSE_DELETION_FAILED("HOUSE_DELETION_FAILED", 500),
    INTERNAL_ERROR("INTERNAL_ERROR", 500);
    private final String type;
    private final Integer code;
}
