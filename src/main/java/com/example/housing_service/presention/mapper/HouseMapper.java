package com.example.housing_service.presention.mapper;

import com.example.housing_service.presention.dataTransferObject.HouseDto;
import com.example.housing_service.presention.dataTransferObject.request.CreationHousingRequest;
import com.example.housing_service.presention.dataTransferObject.request.UpdateHousingRequest;
import com.example.housing_service.persistence.model.House;
import com.example.housing_service.persistence.model.Image;
import org.mapstruct.*;

import java.util.HashSet;
import java.util.Set;

@Mapper(
        config = MapperConfig.class,
        uses = {ImageMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface HouseMapper extends EntityMapper<House, HouseDto> {
    @Mapping(source = "images", target = "images", qualifiedByName = "imageStringToEntity")
    House fromCreationToEntity(CreationHousingRequest request);

    @Mapping(source = "images", target = "images", qualifiedByName = "imageStringToEntity")
    House fromUpdatingToEntity(@MappingTarget House house, UpdateHousingRequest request);


    @Named("imageStringToEntity")
    public default Set<Image> creationRequestToEntity(Set<String> images){
        Set<Image> listMappingEntity = new HashSet<>();

        images.forEach(image -> {
            Image entity = Image.builder().link(image).build();
            listMappingEntity.add(entity);
        });
        return listMappingEntity;
    }

}
