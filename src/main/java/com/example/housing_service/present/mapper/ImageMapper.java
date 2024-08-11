package com.example.housing_service.present.mapper;

import com.example.housing_service.dto.ImageDto;
import com.example.housing_service.persistence.entity.Image;
import org.mapstruct.Mapper;

@Mapper(
        config = MapperConfig.class
)
public interface ImageMapper extends EntityMapper<Image, ImageDto>{
}
