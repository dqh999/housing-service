package com.example.housing_service.presention.mapper;

import com.example.housing_service.presention.dataTransferObject.AttachmentDTO;
import com.example.housing_service.persistence.model.AttachmentEntity;
import org.mapstruct.Mapper;

@Mapper(
        config = MapperConfig.class
)
public interface AttachmentMapper extends EntityMapper<AttachmentEntity, AttachmentDTO>{
}
