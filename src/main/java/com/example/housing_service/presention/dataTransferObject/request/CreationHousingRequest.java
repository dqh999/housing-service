package com.example.housing_service.presention.dataTransferObject.request;

import com.example.housing_service.presention.dataTransferObject.AttachmentDTO;
import com.example.housing_service.presention.dataTransferObject.UserDTO;
import com.example.housing_service.util.RoomCategory;
import com.example.housing_service.util.RoomType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    RoomType roomType;
    RoomCategory roomCategory;
    
    String title;
    String description;
    Double price;
    String thumbnail;

    String address;
    Double latitude;
    Double longitude;

    String ownerName;
    String contactPhoneNumber;

    Integer houseArea;
    Integer maxOccupancy;


    Boolean hasPrivateBathroom;
    Boolean hasWifi;
    Boolean hasFireSafety;
    Boolean hasEmergencyExit;
    Boolean hasMezzanine;
    Boolean hasWaterHeater;
    Boolean hasOutdoorFeatures;
    Boolean hasCookingArea;
    Boolean hasAirConditioner;
    Boolean hasFridge;
    Boolean hasParking;


    Boolean nearMarket;
    Boolean nearSupermarket;
    Boolean nearHospital;
    Boolean nearSchool;
    Boolean nearPark;
    Boolean nearGym;
    Boolean nearBus;
    Boolean nearMainRoad;

    Set<AttachmentDTO> attachments;
}
