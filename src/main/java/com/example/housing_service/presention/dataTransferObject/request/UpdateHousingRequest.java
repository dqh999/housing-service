package com.example.housing_service.presention.dataTransferObject.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateHousingRequest {
    String address;

    String title;


    String description;

    Double price;

    String roomType; // can change to enum

    String roomCategory; // can change to enum

    String contactPhoneNumber;

    Integer area;

    Boolean sharedWithOwner;


    String maxOccupancy;

    Boolean hasFireSafety;


    Boolean cleaningType;


    Boolean hasOutdoorFeatures;


    Boolean hasAirConditioner;

    Boolean hasWaterHeater;

    Set<String> attachments;
}
