package com.example.housing_service.presention.service.impl;

import com.example.housing_service.persistence.model.house.HouseEntity;
import com.example.housing_service.persistence.model.house.HouseFavoriteEntity;
import com.example.housing_service.persistence.repository.HousingFavoriteRepository;
import com.example.housing_service.presention.dataTransferObject.UserDTO;
import com.example.housing_service.presention.service.HousingFavoriteService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
@Slf4j
public class HousingFavoriteServiceImpl implements HousingFavoriteService {
    HousingFavoriteRepository housingFavoriteRepository;
    @Override
    @Transactional
    public void addFavoriteHouse(UserDTO userRequest, Long houseId) {
        HouseFavoriteEntity newHouseFavoriteEntity = HouseFavoriteEntity.builder()
                .userId(userRequest.getUserId())
                .houseId(houseId)
                .build();
        housingFavoriteRepository.save(newHouseFavoriteEntity);
    }

    @Override
    public long getTotalFavorites(Long houseId) {
        return housingFavoriteRepository.countByHouseId(houseId);
    }

    @Override
    @Transactional
    public void removeFavoriteHouse(UserDTO userRequest, Long houseId) {
        housingFavoriteRepository.deleteByUserIdAndHouseId(userRequest.getUserId(), houseId);
    }

    @Override
    public boolean isHouseFavorite(UserDTO userRequest, Long houseId) {
        return housingFavoriteRepository.existsByUserIdAndHouseId(userRequest.getUserId(), houseId);
    }
}
