package com.example.housing_service.presention.dataTransferObject.response;

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
public class CreationHousingResponse {
    Long id;
    String roomType;
    String title;
    String description;
    Double price;
    Set<String> attachments;
}
