package com.example.housing_service.persistence.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Table(name = "tbl_houses")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "House")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class House extends AbstractEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "user_id")
    Integer userId;

    @Column(name = "address")
    String address;

    @Column(name = "title")
    String title;

    @Column(name = "description")
    String description;

    @Column(name = "price")
    Double price;

    @Column(name = "room_type")
    String roomType; // can change to enum

    @Column(name = "room_category")
    String roomCategory; // can change to enum

    @Column(name = "contact_phone_number")
    String contactPhoneNumber;

    @Column(name = "area")
    Integer area;

    @Column(name = "shared_with_owner")
    Boolean sharedWithOwner;

    @Column(name = "max_ occupancy")
    String maxOccupancy;

    @Column(name = "has_fire_safety")
    Boolean hasFireSafety;

    @Column(name = "cleaning_type")
    Boolean cleaningType;

    @Column(name = "has_outdoor_features")
    Boolean hasOutdoorFeatures;

    @Column(name = "has_air_condition")
    Boolean hasAirConditioner;

    @Column(name = "has_water_heating")
    Boolean hasWaterHeater;

    @OneToMany(
            mappedBy = "house",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Column(name = "images")
    Set<Image> images;

}
