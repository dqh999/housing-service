package com.example.housing_service.presention.service;

import com.example.housing_service.presention.dataTransferObject.HouseDto;
import com.example.housing_service.presention.dataTransferObject.response.PageResponse;
import com.example.housing_service.presention.dataTransferObject.request.CreationHousingRequest;
import com.example.housing_service.presention.dataTransferObject.request.PositionRequest;
import com.example.housing_service.presention.dataTransferObject.request.UpdateHousingRequest;
import org.springframework.data.domain.Pageable;

public interface HousingService {
    HouseDto createHousing(CreationHousingRequest request);

    PageResponse<?> findAll();
    void updateHousing(Long houseId, UpdateHousingRequest request) throws Exception;

    void reportHousing(Long houseId);

    void identifyHousing();

    PageResponse<HouseDto> findByAddress(String address, Pageable pageable);


    PageResponse<HouseDto> findByPosition(PositionRequest request, Pageable pageable);
}
