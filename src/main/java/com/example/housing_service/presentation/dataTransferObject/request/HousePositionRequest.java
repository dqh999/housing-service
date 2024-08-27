package com.example.housing_service.presentation.dataTransferObject.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter @Builder @FieldDefaults(level = AccessLevel.PRIVATE)
public class HousePositionRequest {
    @Min(value = -90, message = "Latitude must be at least -90")
    @Max(value = 90, message = "Latitude must be at most 90")
    Double latitude;
    @Min(value = -180, message = "Longitude must be at least -180")
    @Max(value = 180, message = "Longitude must be at most 180")
    Double longitude;
    @Min(value = 0, message = "Radius must be at least 0")
    Integer radius;
}