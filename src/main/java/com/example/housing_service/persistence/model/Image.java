package com.example.housing_service.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Table(name = "tbl_images")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Image")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Image extends AbstractEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    Integer id;


    @Column(name = "image_link")
    String link;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "house_id")
    House house;
}
