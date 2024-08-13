package com.example.housing_service.presention.dataTransferObject;

import com.example.housing_service.persistence.model.AbstractEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttachmentDTO extends AbstractEntity {
    Long attachmentId;
    String attachmentType;
    String source;
}
