package com.example.housing_service.persistence.model.house;

import com.example.housing_service.persistence.model.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity(name = "Favorite")
@Table(name = "tbl_favorites")
@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HouseFavoriteEntity extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    Long id;
    @Column(name = "user_id")
    Long userId;
    @Column(name = "house_id")
    Long houseId;
}