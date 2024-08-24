package com.example.housing_service.presention.dataTransferObject.request;

import com.example.housing_service.presention.dataTransferObject.AttachmentDTO;
import com.example.housing_service.util.RoomCategory;
import com.example.housing_service.util.RoomType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class CreationHousingRequest {

    @NotNull(message = "Room type must not be null")
    RoomType roomType;
    @NotNull(message = "Room category must not be null")
    RoomCategory roomCategory;

    @NotBlank(message = "Title must not be blank")
    @Size(max = 255, message = "Title must be less than 255 characters")
    String title;
    @NotBlank(message = "Description must not be blank")
    @NotNull(message = "Price must not be null")
    Double price;
    @NotNull(message = "Thumbnail must not be null")
    String thumbnail;

    @NotBlank(message = "Address must not be blank")
    @Size(max = 255, message = "Address must be less than 255 characters")
    String address;
    @NotNull(message = "Latitude must not be null")
    Double latitude;
    @NotNull(message = "Longitude must not be null")
    Double longitude;

    @NotBlank(message = "Owner name must not be blank")
    @Size(max = 255, message = "Owner name must be less than 255 characters")
    String ownerName;
    @NotBlank(message = "Contact phone number must not be blank")
    @Size(max = 20, message = "Contact phone number must be less than 20 characters")
    @Pattern(regexp = "^((\\+84)|(0))\\d{9,10}$", message = "Invalid phone number.")
    String contactPhoneNumber;

    @NotNull(message = "House area must not be null")
    Integer houseArea;
    @NotNull(message = "Max occupancy must not be null")
    Integer maxOccupancy;

    @NotNull(message = "hasPrivateBathroom must not be null")
    Boolean hasPrivateBathroom;
    @NotNull(message = "hasWifi must not be null")
    Boolean hasWifi;
    @NotNull(message = "hasFireSafety must not be null")
    Boolean hasFireSafety;
    @NotNull(message = "hasEmergencyExit must not be null")
    Boolean hasEmergencyExit;
    @NotNull(message = "hasMezzanine must not be null")
    Boolean hasMezzanine;
    @NotNull(message = "hasWaterHeater must not be null")
    Boolean hasWaterHeater;
    @NotNull(message = "hasOutdoorFeatures must not be null")
    Boolean hasOutdoorFeatures;
    @NotNull(message = "hasCookingArea must not be null")
    Boolean hasCookingArea;
    @NotNull(message = "hasAirConditioner must not be null")
    Boolean hasAirConditioner;
    @NotNull(message = "hasFridge must not be null")
    Boolean hasFridge;
    @NotNull(message = "hasParking must not be null")
    Boolean hasParking;
    @NotNull(message = "nearMarket must not be null")
    Boolean nearMarket;
    @NotNull(message = "nearSupermarket must not be null")
    Boolean nearSupermarket;
    @NotNull(message = "nearHospital must not be null")
    Boolean nearHospital;
    @NotNull(message = "nearSchool must not be null")
    Boolean nearSchool;
    @NotNull(message = "nearPark must not be null")
    Boolean nearPark;
    @NotNull(message = "nearGym must not be null")
    Boolean nearGym;
    @NotNull(message = "nearBus must not be null")
    Boolean nearBus;
    @NotNull(message = "nearMainRoad must not be null")
    Boolean nearMainRoad;

    @NotNull(message = "Attachments must not be null")
    Set<AttachmentDTO> attachments;
}
