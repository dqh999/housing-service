package com.example.housing_service.presention.dataTransferObject;

import com.example.housing_service.persistence.model.AbstractEntity;
import lombok.*;


@Getter
@Setter
@Builder
public class ImageDto extends AbstractEntity {
    Integer id;
    String link;
}
