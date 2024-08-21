package com.example.housing_service.util;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum HouseStatus {
    PENDING("PENDING", 0),
    APPROVED("APPROVED", 1);
    private final String type;
    private final int code;

}