package com.example.housing_service.presention.mapper;

import com.example.housing_service.presention.dataTransferObject.response.HouseResponse;
import com.example.housing_service.presention.dataTransferObject.request.CreationHousingRequest;
import com.example.housing_service.presention.dataTransferObject.request.UpdateHousingRequest;
import com.example.housing_service.persistence.model.house.HouseEntity;
import com.example.housing_service.persistence.model.AttachmentEntity;
import org.mapstruct.*;

import java.util.HashSet;
import java.util.Set;

@Mapper(
        config = MapperConfig.class,
        uses = {AttachmentMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface HouseMapper extends EntityMapper<HouseEntity, HouseResponse> {
    @Mapping(source = "attachments", target = "attachments", qualifiedByName = "attachmentStringToEntity")
    HouseEntity fromCreationToEntity(CreationHousingRequest request);

    @Mapping(source = "attachments", target = "attachments", qualifiedByName = "attachmentStringToEntity")
    HouseEntity fromUpdatingToEntity(@MappingTarget HouseEntity houseEntity, UpdateHousingRequest request);

    @Named("attachmentStringToEntity")
    default Set<AttachmentEntity> creationRequestToEntity(Set<String> attachments) {
        Set<AttachmentEntity> listMappingEntity = new HashSet<>();

        if (attachments != null){
            attachments.forEach(image -> {
                AttachmentEntity entity = AttachmentEntity.builder().build();
                listMappingEntity.add(entity);
            });
        }
        return listMappingEntity;
    }
}