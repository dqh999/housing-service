package com.example.housing_service.util;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RoomType {
    RENTING("RENTING",0),
    ROOMMATE("ROOMMATE",2),
    SUBLET("SUBLET",1);
    private final String type;
    private final int code;
}
