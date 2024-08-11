package com.example.housing_service.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class PositionRequest {
    Double latitude;
    Double longitude;
    Integer radius;
}
