package com.example.housing_service.presention.dataTransferObject.response;

import com.example.housing_service.presention.dataTransferObject.AttachmentDTO;
import com.example.housing_service.presention.dataTransferObject.PosterDTO;
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
public class HouseResponse {
    Long houseId;
    PosterDTO poster;

    String address;

    String title;


    String description;

    Double price;

    String roomType;

    String roomCategory; // can change to enum

    String contactPhoneNumber;

    Integer area;



    String maxOccupancy;

    Boolean hasFireSafety;


    Boolean cleaningType;


    Boolean hasOutdoorFeatures;


    Boolean hasAirConditioner;

    Boolean hasWaterHeater;


    Set<AttachmentDTO> attachments;
    int totalFavorites;
}
