package com.example.housing_service.present.Service.impl;

import com.example.housing_service.dto.HouseDto;
import com.example.housing_service.dto.response.PageResponse;
import com.example.housing_service.dto.request.CreationHousingRequest;
import com.example.housing_service.dto.request.PositionRequest;
import com.example.housing_service.dto.request.UpdateHousingRequest;
import com.example.housing_service.present.Service.HousingService;
import com.example.housing_service.persistence.entity.House;
import com.example.housing_service.persistence.entity.Image;
import com.example.housing_service.present.mapper.HouseMapper;
import com.example.housing_service.present.mapper.ImageMapper;
import com.example.housing_service.persistence.entity.repository.HousingRepository;
import com.example.housing_service.persistence.entity.repository.ImageRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
@Slf4j
public class HousingServiceImpl implements HousingService {
    HousingRepository housingRepository;
    ImageRepository imageRepository;
    ImageMapper imageMapper;
    HouseMapper houseMapper;


    // bo method nay

    @Override
    public PageResponse<?> findAll() {
        return PageResponse.builder()
                .data(housingRepository.findAll())
                .build();
    }

    @Override
    public HouseDto createHousing(CreationHousingRequest request) {

        // housing_db save house
        House house = housingRepository.save(houseMapper.fromCreationToEntity(request));

        // reference images to house
        updateImageToHouse(house.getImages(), house);
        // user_db update house to user


        return houseMapper.toDto(house);
    }

    @Override
    public void updateHousing(Long houseId, UpdateHousingRequest request) throws Exception {
        var updatingHouse = housingRepository.findById(houseId).orElseThrow(() -> new Exception("HouseId: "+houseId + " not found"));


        // delete old images
        updatingHouse.getImages().forEach(image -> {
            var oldImage = imageRepository.findById(image.getId()).orElseThrow();
            imageRepository.delete(oldImage);
        });

        // then save new images... need change
        housingRepository.save(houseMapper.fromUpdatingToEntity(updatingHouse, request));

        updateImageToHouse(updatingHouse.getImages(), updatingHouse);
    }

    @Override
    public void reportHousing(Long houseId) {

    }

    @Override
    public void identifyHousing() {

    }

    @Override
    public PageResponse<HouseDto> findByAddress(String address, Pageable pageable) {
        var result = housingRepository.findAllByAddressContainingIgnoreCase(address, pageable);
        return PageResponse.<HouseDto>builder()
                .currentPage(result.getNumber())
                .total(result.getTotalPages())
                .pageSize(result.getSize())
                .build();
    }

    @Override
    public PageResponse<HouseDto> findByPosition(PositionRequest request, Pageable pageable) {
        return null;
    }


    void updateImageToHouse(Set<Image> images, House house){
        images.forEach(image -> {
            var updateImage = imageRepository.findById(image.getId()).orElseThrow();
            updateImage.setHouse(house);
            imageRepository.save(updateImage);
        });
    }
}
