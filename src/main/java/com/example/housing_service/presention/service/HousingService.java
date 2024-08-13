package com.example.housing_service.presention.service;

import com.example.housing_service.persistence.model.house.HouseEntity;
import com.example.housing_service.presention.dataTransferObject.request.UserRequest;
import com.example.housing_service.presention.dataTransferObject.response.HouseResponse;
import com.example.housing_service.presention.dataTransferObject.response.PageResponse;
import com.example.housing_service.presention.dataTransferObject.request.CreationHousingRequest;
import com.example.housing_service.presention.dataTransferObject.request.HousePositionRequest;
import com.example.housing_service.presention.dataTransferObject.request.UpdateHousingRequest;
import org.springframework.data.domain.Pageable;

public interface HousingService {
    HouseResponse createHousing(UserRequest userRequest,CreationHousingRequest request);

    PageResponse<?> findAll();
    void updateHousing(Long houseId, UpdateHousingRequest request) throws Exception;

    void reportHousing(Long houseId);

    void identifyHousing();

    PageResponse<HouseResponse> findByAddress(String address, Pageable pageable);


    PageResponse<HouseEntity> findByPosition(HousePositionRequest request, Pageable pageable);
}
