package com.example.housing_service.util;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RoomCategory {
    STUDIO("STUDIO", 0),
    APARTMENT("APARTMENT", 1),
    MINI_APARTMENT("MINI_APARTMENT", 2),
    HOUSE("HOUSE", 3);
    private final String type;
    private final int code;

}
