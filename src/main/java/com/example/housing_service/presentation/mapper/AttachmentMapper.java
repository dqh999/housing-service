package com.example.housing_service.presentation.mapper;

import com.example.housing_service.presentation.dataTransferObject.AttachmentDTO;
import com.example.housing_service.persistence.model.AttachmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        config = MapperConfig.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AttachmentMapper extends EntityMapper<AttachmentEntity, AttachmentDTO>{
    @Mapping(source = "id", target = "attachmentId")
    @Mapping(source = "attachmentType", target = "type")
    AttachmentDTO toDto(AttachmentEntity attachmentEntity);
}
