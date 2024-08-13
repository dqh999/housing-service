package com.example.housing_service.presention.service.impl;

import com.example.housing_service.persistence.repository.AttachmentRepository;
import com.example.housing_service.persistence.repository.HousingRepository;
import com.example.housing_service.presention.dataTransferObject.request.UserRequest;
import com.example.housing_service.presention.dataTransferObject.response.HouseResponse;
import com.example.housing_service.presention.dataTransferObject.request.HousePositionRequest;
import com.example.housing_service.presention.dataTransferObject.response.PageResponse;
import com.example.housing_service.presention.dataTransferObject.request.CreationHousingRequest;
import com.example.housing_service.presention.dataTransferObject.request.UpdateHousingRequest;
import com.example.housing_service.presention.service.HousingService;
import com.example.housing_service.persistence.model.house.HouseEntity;
import com.example.housing_service.persistence.model.AttachmentEntity;
import com.example.housing_service.presention.mapper.HouseMapper;
import com.example.housing_service.presention.mapper.AttachmentMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
@Slf4j
public class HousingServiceImpl implements HousingService{
    HousingRepository housingRepository;
    AttachmentRepository attachmentRepository;
    AttachmentMapper imageMapper;
    HouseMapper houseMapper;


    // bo method nay

    @Override
    public PageResponse<?> findAll() {
        return PageResponse.builder()
                .build();
    }

    @Override
    public HouseResponse createHousing(UserRequest userRequest,CreationHousingRequest request) {

        // housing_db save house
        var houseEntity = houseMapper.fromCreationToEntity(request);
        houseEntity.setPosterId(userRequest.getUserId());
        housingRepository.save(houseEntity);
        // reference images to house
//        updateImageToHouse(houseEntity.getAttachmentEntities(), houseEntity);
        // user_db update house to user


        return houseMapper.toDto(houseEntity);
    }

    @Override
    public void updateHousing(Long houseId, UpdateHousingRequest request) throws Exception {
        var updatingHouse = housingRepository.findById(houseId).orElseThrow(() -> new Exception("HouseId: "+houseId + " not found"));


        // delete old images
//        updatingHouse.getAttachmentEntities().forEach(image -> {
//            var oldImage = imageRepository.findById(image.getId()).orElseThrow();
//            imageRepository.delete(oldImage);
//        });

        // then save new images... need change
        housingRepository.save(houseMapper.fromUpdatingToEntity(updatingHouse, request));

//        updateImageToHouse(updatingHouse.getAttachmentEntities(), updatingHouse);
    }

    @Override
    public void reportHousing(Long houseId) {

    }

    @Override
    public void identifyHousing() {

    }

    @Override
    public PageResponse<HouseResponse> findByAddress(String address, Pageable pageable) {
        var result = housingRepository.findAllByAddressContainingIgnoreCase(address, pageable);
        List<HouseEntity> data = result.getContent();



        return PageResponse.<HouseResponse>builder()
                .currentPage(result.getNumber())
                .total(result.getTotalPages())
                .pageSize(result.getSize())
                .data(result.getContent().stream().map(house -> {
                    return HouseResponse.builder()
                            .houseId(house.getId())
                            .build();
                }).collect(Collectors.toList()))
                .build();
    }

    @Override
    public PageResponse<HouseEntity> findByPosition(HousePositionRequest request, Pageable pageable) {
        int radiusInMeters = request.getRadius() * 1000;
        var result = housingRepository.findAllByDistanceWithin(request.getLongitude(),
                request.getLatitude(),
                radiusInMeters,
                pageable);
        return PageResponse.<HouseEntity>builder()
                .currentPage(result.getNumber())
                .total(result.getTotalPages())
                .pageSize(result.getSize())
                .data(result.getContent())
                .build();
    }
    void updateImageToHouse(Set<AttachmentEntity> attachmentEntities, HouseEntity houseEntity){
        attachmentEntities.forEach(attachment -> {
            var updateImage = attachmentRepository.findById(attachment.getId()).orElseThrow();
            updateImage.setHouseEntity(houseEntity);
            attachmentRepository.save(updateImage);
        });
    }

}
