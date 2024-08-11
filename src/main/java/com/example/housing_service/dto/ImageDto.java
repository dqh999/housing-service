package com.example.housing_service.dto;

import com.example.housing_service.persistence.entity.AbstractEntity;
import lombok.*;


@Getter
@Setter
@Builder
public class ImageDto extends AbstractEntity {
    Integer id;
    String link;
}
