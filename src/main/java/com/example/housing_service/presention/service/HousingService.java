package com.example.housing_service.presention.service;

import com.example.housing_service.presention.dataTransferObject.UserDTO;
import com.example.housing_service.presention.dataTransferObject.request.HouseSearchRequest;
import com.example.housing_service.presention.dataTransferObject.response.HouseResponse;
import com.example.housing_service.presention.dataTransferObject.response.PageResponse;
import com.example.housing_service.presention.dataTransferObject.request.CreationHousingRequest;
import com.example.housing_service.presention.dataTransferObject.request.HousePositionRequest;
import com.example.housing_service.presention.dataTransferObject.request.UpdateHousingRequest;
import org.springframework.data.domain.Pageable;

public interface HousingService {
    HouseResponse createHousing(UserDTO userRequest, CreationHousingRequest request);
    void updateHousing(Long houseId, UpdateHousingRequest request) throws Exception;

    HouseResponse getHouseBySlug(String slug)throws Exception;


    PageResponse<HouseResponse> findAllByPosterId(UserDTO userRequest,Pageable pageable);
    PageResponse<HouseResponse> findTopFavorite(Pageable pageable);
    PageResponse<HouseResponse> findAllVerified(Pageable pageable);
    PageResponse<HouseResponse> findHouse(HouseSearchRequest request);

//    PageResponse<HouseResponse> findByAddress(String address, Pageable pageable);
//
//
//    PageResponse<HouseResponse> findByPosition(HousePositionRequest request, Pageable pageable);
}
