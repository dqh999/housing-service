package com.example.housing_service.presention.dataTransferObject;

import com.example.housing_service.persistence.model.AbstractEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttachmentDTO extends AbstractEntity {
    Long attachmentId;
    String type;
    String attachmentName;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
