package com.example.housing_service.presention.dataTransferObject.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreationHousingRequest {
    List<UserRequest> userRequest;
    Integer userId;
    String address;

    @NotBlank(message = "${title} cannot be blank")
    String title;

    String thumbnail;
    String description;

    Double price;

    String roomType; // can change to enum

    String roomCategory; // can change to enum

    String ownerName;
    String contactPhoneNumber;

    Integer area;



    String maxOccupancy;

    Boolean hasFireSafety;


    Boolean cleaningType;


    Boolean hasOutdoorFeatures;


    Boolean hasAirConditioner;

    Boolean hasWaterHeater;

    Set<String> attachments;
}
