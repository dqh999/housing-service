package com.example.housing_service.persistence.model;

import com.example.housing_service.persistence.model.house.HouseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Table(name = "tbl_attachments")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "A")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttachmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachment_id")
    Long id;
    @Column(name = "attachment_type")
    String attachmentType;
    @Column(name = "source")
    String source;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "house_id")
    HouseEntity houseEntity;
}
