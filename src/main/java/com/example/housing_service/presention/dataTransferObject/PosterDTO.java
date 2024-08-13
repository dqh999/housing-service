package com.example.housing_service.presention.dataTransferObject;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data @Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PosterDTO {
    Long userId;
    String fullName;
    String avatar;
}
