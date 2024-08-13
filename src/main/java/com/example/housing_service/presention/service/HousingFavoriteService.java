package com.example.housing_service.presention.service;

import com.example.housing_service.persistence.model.house.HouseEntity;
import com.example.housing_service.presention.dataTransferObject.request.UserRequest;

import java.util.List;

public interface HousingFavoriteService {
    void addFavoriteHouse(UserRequest userRequest, Long houseId);
    long getTotalFavorites(Long houseId);
    void removeFavoriteHouse(UserRequest userRequest, Long houseId);

    List<HouseEntity> getFavoriteHouses(Long userId);
    boolean isHouseFavorite(UserRequest userRequest, Long houseId);
}