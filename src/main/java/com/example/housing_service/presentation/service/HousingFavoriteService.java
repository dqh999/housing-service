package com.example.housing_service.presentation.service;

import com.example.housing_service.presentation.dataTransferObject.UserDTO;

public interface HousingFavoriteService {
    void addFavoriteHouse(UserDTO userRequest, Long houseId);
    long getTotalFavorites(Long houseId);
    void removeFavoriteHouse(UserDTO userRequest, Long houseId);
    boolean isHouseFavorite(UserDTO userRequest, Long houseId);
}