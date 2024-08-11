package com.example.housing_service.presention.mapper;

import com.example.housing_service.presention.dataTransferObject.ImageDto;
import com.example.housing_service.persistence.model.Image;
import org.mapstruct.Mapper;

@Mapper(
        config = MapperConfig.class
)
public interface ImageMapper extends EntityMapper<Image, ImageDto>{
}
