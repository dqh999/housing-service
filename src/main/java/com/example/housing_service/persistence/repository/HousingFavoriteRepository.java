package com.example.housing_service.persistence.repository;

import com.example.housing_service.persistence.model.house.HouseFavoriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HousingFavoriteRepository extends JpaRepository<HouseFavoriteEntity,Long> {
    long countByHouseId(Long houseId);
    void deleteByUserIdAndHouseId(Long userId, Long houseId);
    boolean existsByUserIdAndHouseId(Long userId, Long houseId);
}
