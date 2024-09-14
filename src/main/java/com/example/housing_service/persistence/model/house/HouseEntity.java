package com.example.housing_service.persistence.model.house;

import com.example.housing_service.persistence.model.AbstractEntity;
import com.example.housing_service.persistence.model.AttachmentEntity;
import com.example.housing_service.util.HouseStatus;
import com.example.housing_service.util.RoomCategory;
import com.example.housing_service.util.RoomType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

import org.locationtech.jts.geom.Point;


@Entity
@Table(name = "tbl_houses")
@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HouseEntity  extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "house_id")
    Long id;
    @Column(name = "poster_id")
    Long posterId;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type", nullable = false)
    RoomType roomType;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_category", nullable = false)
    RoomCategory roomCategory;
    @Column(name = "flag_code")
    Byte flagCode;
    @Column(name = "title", nullable = false)
    String title;
    @Column(name = "description")
    String description;
    @Column(name = "thumbnail")
    String thumbnail;
    @Column(name = "price", nullable = false)
    Double price;
    @Column(name = "slug")
    String slug;
    @Column(name = "is_verified")
    Boolean isVerified;
    @Column(name = "total_views")
    Integer totalViews;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    HouseStatus status;

    @Column(name = "address", nullable = false)
    String address;
    @Column(name = "latitude")
    Double latitude;
    @Column(name = "longitude")
    Double longitude;
    @Column(columnDefinition = "geometry(Point, 4326)")
    Point geom;

    @Column(name = "owner_name")
    String ownerName;
    @Column(name = "contact_phone_number")
    String contactPhoneNumber;
    @Column(name = "house_area")
    Integer houseArea;
    @Column(name = "max_occupancy")
    Integer maxOccupancy;


    @Column(name = "wifi_fee")
    Double wifiFee;
    @Column(name = "electricity_fee")
    Double electricityFee;
    @Column(name = "water_fee")
    Double waterFee;
    @Column(name = "common_service_fee")
    Double commonServiceFee;

    // Amenities
    @Column(name = "has_private_bathroom")
    Boolean hasPrivateBathroom;
    @Column(name = "has_wifi")
    Boolean hasWifi;
    @Column(name = "has_fire_safety")
    Boolean hasFireSafety;
    @Column(name = "has_emergency_exit")
    Boolean hasEmergencyExit;
    @Column(name = "has_mezzanine")
    Boolean hasMezzanine;
    @Column(name = "has_water_heater")
    Boolean hasWaterHeater;
    @Column(name = "has_outdoor_features")
    Boolean hasOutdoorFeatures;
    @Column(name = "has_cooking_area")
    Boolean hasCookingArea;
    @Column(name = "has_air_conditioner")
    Boolean hasAirConditioner;
    @Column(name = "has_fridge")
    Boolean hasFridge;
    @Column(name = "has_parking")
    Boolean hasParking;

    // Surrounding environment
    @Column(name = "near_market")
    Boolean nearMarket;
    @Column(name = "near_supermarket")
    Boolean nearSupermarket;
    @Column(name = "near_hospital")
    Boolean nearHospital;
    @Column(name = "near_school")
    Boolean nearSchool;
    @Column(name = "near_park")
    Boolean nearPark;
    @Column(name = "near_gym")
    Boolean nearGym;
    @Column(name = "near_bus")
    Boolean nearBus;
    @Column(name = "near_main_road")
    Boolean nearMainRoad;

    @OneToMany(mappedBy = "houseEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<AttachmentEntity> attachments;
}
