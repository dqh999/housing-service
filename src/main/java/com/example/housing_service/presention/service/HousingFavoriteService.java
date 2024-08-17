package com.example.housing_service.presention.service;

import com.example.housing_service.persistence.model.house.HouseEntity;
import com.example.housing_service.presention.dataTransferObject.UserDTO;

import java.util.List;

public interface HousingFavoriteService {
    void addFavoriteHouse(UserDTO userRequest, Long houseId);
    long getTotalFavorites(Long houseId);
    void removeFavoriteHouse(UserDTO userRequest, Long houseId);

    List<HouseEntity> getFavoriteHouses(Long userId);
    boolean isHouseFavorite(UserDTO userRequest, Long houseId);
}