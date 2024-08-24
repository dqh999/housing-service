package com.example.housing_service.presention.mapper;

import com.example.housing_service.persistence.model.house.HouseEntity;
import com.example.housing_service.presention.dataTransferObject.AttachmentDTO;
import com.example.housing_service.persistence.model.AttachmentEntity;
import com.example.housing_service.presention.dataTransferObject.response.HouseResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        config = MapperConfig.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AttachmentMapper extends EntityMapper<AttachmentEntity, AttachmentDTO>{
    @Mapping(source = "id", target = "attachmentId")
    AttachmentDTO toDto(AttachmentEntity attachmentEntity);
}
