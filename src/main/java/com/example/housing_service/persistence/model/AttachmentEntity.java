package com.example.housing_service.persistence.model;

import com.example.housing_service.persistence.model.house.HouseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "tbl_house_attachments")
@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttachmentEntity {
    @Id @Column(name = "attachment_id")
    Long id;
    @Column(name = "attachment_type")
    String attachmentType;
    @Column(name = "attachment_name")
    String attachmentName;
    @Column(name = "source")
    String source;
    @ManyToOne
    @JoinColumn(name = "house_id")
    HouseEntity houseEntity;
}
