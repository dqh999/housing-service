package com.example.housing_service.presentation.mapper;

import com.example.housing_service.presentation.dataTransferObject.response.HouseResponse;
import com.example.housing_service.presentation.dataTransferObject.request.CreationHousingRequest;
import com.example.housing_service.presentation.dataTransferObject.request.UpdateHousingRequest;
import com.example.housing_service.persistence.model.house.HouseEntity;
import org.mapstruct.*;

@Mapper(
        config = MapperConfig.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface HouseMapper extends EntityMapper<HouseEntity, HouseResponse> {
    @Mapping(target = "attachments", ignore = true)
    HouseEntity fromCreationToEntity(CreationHousingRequest request);
    @Mapping(target = "attachments", ignore = true)
    HouseEntity fromUpdatingToEntity(@MappingTarget HouseEntity houseEntity, UpdateHousingRequest request);
    @Mapping(source = "id", target = "houseId")
    @Mapping(target = "attachments", ignore = true)
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    HouseResponse toDto(HouseEntity houseEntity);
}