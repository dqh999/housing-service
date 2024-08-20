package com.example.housing_service.presention.service.impl;

import com.example.housing_service.intergration.client.UserClient;
import com.example.housing_service.persistence.repository.AttachmentRepository;
import com.example.housing_service.persistence.repository.HousingRepository;
import com.example.housing_service.presention.dataTransferObject.AttachmentDTO;
import com.example.housing_service.presention.dataTransferObject.UserDTO;
import com.example.housing_service.presention.dataTransferObject.request.*;
import com.example.housing_service.presention.dataTransferObject.response.HouseResponse;
import com.example.housing_service.presention.dataTransferObject.response.PageResponse;
import com.example.housing_service.presention.service.HousingService;
import com.example.housing_service.persistence.model.house.HouseEntity;
import com.example.housing_service.persistence.model.AttachmentEntity;
import com.example.housing_service.presention.mapper.HouseMapper;
import com.example.housing_service.presention.mapper.AttachmentMapper;
import com.example.housing_service.util.SlugGenerator;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
@Slf4j
public class HousingServiceImpl implements HousingService{
    HousingRepository housingRepository;
    AttachmentRepository attachmentRepository;
    AttachmentMapper attachmentMapper;
    HouseMapper houseMapper;
    UserClient userClient;

    @Override
    @Transactional
    public HouseResponse createHousing(UserDTO userRequest, CreationHousingRequest request) {
        var houseEntity = houseMapper.fromCreationToEntity(request);
        var slug = SlugGenerator.generateUniqueSlug(request.getTitle());
        houseEntity.setSlug(slug);
        houseEntity.setPosterId(userRequest.getUserId());
        housingRepository.save(houseEntity);
        List<AttachmentEntity> attachmentEntities = request.getAttachments().stream()
                .map(attachmentDTO -> {
                    return AttachmentEntity.builder()
                            .houseEntity(houseEntity)
                            .attachmentType(attachmentDTO.getAttachmentType())
                            .attachmentName(attachmentDTO.getAttachmentName())
                            .source(attachmentDTO.getSource())
                            .build();
                })
                .collect(Collectors.toList());
        attachmentRepository.saveAll(attachmentEntities);
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
    public HouseResponse getHouseById(Long houseId) throws Exception {
        var result = housingRepository.findById(houseId)
                .orElseThrow(() -> new Exception(String.format("House with id %s not found", houseId)));
        var attachments = result.getAttachments();
        var response = houseMapper.toDto(result);
        response.setAttachments(attachmentMapper.toDto(attachments));
        return response;
    }

    @Override
    public HouseResponse getHouseBySlug(String slug) throws Exception {
        var result = housingRepository.findBySlug(slug)
                .orElseThrow(() -> new Exception(String.format("House with slug %s not found", slug)));
        var attachments = result.getAttachments();
        var response = houseMapper.toDto(result);
        response.setAttachments(attachmentMapper.toDto(attachments));
        return response;
    }

    @Override
    public PageResponse<HouseResponse> findTopFavorite(Pageable pageable) {
        var result = housingRepository.findTopFavorite(pageable);
        return PageResponse.<HouseResponse>builder()
                .totalPages(result.getTotalPages())
                .pageSize(result.getSize())
                .build();
    }

    @Override
    public PageResponse<HouseResponse> findHouse(HouseSearchRequest request) {
        Pageable pageable = PageRequest.of(0, 10);
        var result = housingRepository.findAll(request.specification(),pageable);

        return PageResponse.<HouseResponse>builder()
                .currentPage(result.getNumber())
                .totalPages(result.getTotalPages())
                .pageSize(result.getSize())
                .data(result.getContent().stream().map(houseEntity -> {
                    HouseResponse houseResponse = houseMapper.toDto(houseEntity);
                    return houseResponse;
                }).collect(Collectors.toList()))
                .build();
    }

    @Override
    public PageResponse<HouseResponse> findByAddress(String address, Pageable pageable) {
        var result = housingRepository.findAllByAddressContainingIgnoreCase(address, pageable);
        List<Long> posterIds = result.stream().map(HouseEntity::getPosterId).collect(Collectors.toList());
        var posters = userClient.fetchUsers(posterIds);
        Map<Long, UserDTO> posterMap = posters.getData().stream()
                .collect(Collectors.toMap(UserDTO::getUserId, Function.identity()));
        return PageResponse.<HouseResponse>builder()
                .currentPage(result.getNumber())
                .totalPages(result.getTotalPages())
                .pageSize(result.getSize())
                .data(result.getContent().stream().map(houseEntity -> {
                    HouseResponse houseResponse = houseMapper.toDto(houseEntity);
                    houseResponse.setPoster(posterMap.get(houseEntity.getPosterId()));
                    return houseResponse;
                }).collect(Collectors.toList()))
                .build();
    }

    @Override
    public PageResponse<HouseResponse> findByPosition(HousePositionRequest request, Pageable pageable) {
        HouseSearchRequest houseSearchRequest = HouseSearchRequest.builder().build();
        Specification<HouseEntity>  data = houseSearchRequest.specification();
        int radiusInMeters = request.getRadius() * 1000;
        var result = housingRepository.findAllByDistanceWithin(request.getLongitude(),
                request.getLatitude(),
                radiusInMeters,
                pageable);
        log.info("result " + result.getContent());
        List<Long> posterIds = result.stream().map(HouseEntity::getPosterId).collect(Collectors.toList());
        var posters = userClient.fetchUsers(posterIds);
        log.info("poster " + posters);
        Map<Long, UserDTO> posterMap = posters.getData().stream()
                .collect(Collectors.toMap(UserDTO::getUserId, Function.identity()));
        return PageResponse.<HouseResponse>builder()
                .totalElements((int) result.getTotalElements())
                .currentPage(result.getNumber())
                .totalPages(result.getTotalPages())
                .pageSize(result.getSize())
                .data(result.stream().map(houseEntity -> {
                    HouseResponse response = houseMapper.toDto(houseEntity);
                    response.setPoster(posterMap.get(houseEntity.getPosterId()));
                    return response;
                }).collect(Collectors.toList()))
                .hasNext(result.hasNext())
                .hasPrevious(result.hasPrevious())
                .build();
    }
    void updateImageToHouse(Set<AttachmentEntity> attachmentEntities, HouseEntity houseEntity){
        attachmentEntities.forEach(attachment -> {
            var updateImage = attachmentRepository.findById(attachment.getId()).orElseThrow();
            updateImage.setHouseEntity(houseEntity);
            attachmentRepository.save(updateImage);
        });
    }
    public static <T> Specification<HouseEntity> hasField(String fieldName, T value) {
        return (root, query, criteriaBuilder) ->
                value != null ? criteriaBuilder.equal(root.get(fieldName), value) : null;
    }
}
