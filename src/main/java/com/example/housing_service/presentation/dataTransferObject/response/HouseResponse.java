package com.example.housing_service.presentation.dataTransferObject.response;

import com.example.housing_service.presentation.dataTransferObject.AttachmentDTO;
import com.example.housing_service.presentation.dataTransferObject.UserDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HouseResponse {
    Long houseId;
    String houseCode;
    String roomCategory;
    String roomType;
    UserDTO poster;

    String title;
    String description;
    Double price;
    String thumbnail;
    String slug;
    Boolean isVerified;
    Byte flagCode;
    Integer totalViews;
    long totalFavorite;
    String status;

    String address;
    Double latitude;
    Double longitude;

    String ownerName;
    String contactPhoneNumber;

    Integer houseArea;
    Integer maxOccupancy;

    Double wifiFee;
    Double electricityFee;
    Double waterFee;
    Double commonServiceFee;

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

    List<AttachmentDTO> attachments;
    int totalFavorites;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
