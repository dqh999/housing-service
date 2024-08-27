package com.example.housing_service.presentation.service;

import com.example.housing_service.presentation.dataTransferObject.UserDTO;
import com.example.housing_service.presentation.dataTransferObject.request.HouseSearchRequest;
import com.example.housing_service.presentation.dataTransferObject.response.HouseResponse;
import com.example.housing_service.presentation.dataTransferObject.response.PageResponse;
import com.example.housing_service.presentation.dataTransferObject.request.CreationHousingRequest;
import com.example.housing_service.presentation.dataTransferObject.request.UpdateHousingRequest;
import org.springframework.data.domain.Pageable;

public interface HousingService {
    HouseResponse createHousing(UserDTO userRequest, CreationHousingRequest request);
    void updateHousing(Long houseId, UpdateHousingRequest request) throws Exception;
    void deletHouseById(Long houseId);

    HouseResponse getHouseBySlug(String slug);
    PageResponse<HouseResponse> findMyHouse(UserDTO userRequest,Pageable pageable);
    PageResponse<HouseResponse> findTopFavorite(Pageable pageable);
    PageResponse<HouseResponse> findAllVerified(Pageable pageable);
    PageResponse<HouseResponse> findHouse(HouseSearchRequest request);
}
