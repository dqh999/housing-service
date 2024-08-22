package com.example.housing_service.presention.service.impl;

import com.example.housing_service.intergration.client.UserClient;
import com.example.housing_service.persistence.repository.AttachmentRepository;
import com.example.housing_service.persistence.repository.HousingRepository;
import com.example.housing_service.presention.dataTransferObject.AttachmentDTO;
import com.example.housing_service.presention.dataTransferObject.UserDTO;
import com.example.housing_service.presention.dataTransferObject.request.*;
import com.example.housing_service.presention.dataTransferObject.response.HouseResponse;
import com.example.housing_service.presention.dataTransferObject.response.PageResponse;
import com.example.housing_service.presention.exception.HousingException;
import com.example.housing_service.presention.service.HousingService;
import com.example.housing_service.persistence.model.house.HouseEntity;
import com.example.housing_service.persistence.model.AttachmentEntity;
import com.example.housing_service.presention.mapper.HouseMapper;
import com.example.housing_service.presention.mapper.AttachmentMapper;
import com.example.housing_service.util.HouseStatus;
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
        houseEntity.setTotalViews(0);
        houseEntity.setStatus(HouseStatus.PENDING);
        var slug = SlugGenerator.generateUniqueSlug(request.getTitle());
        houseEntity.setSlug(slug);
        houseEntity.setPosterId(userRequest.getUserId());
        housingRepository.save(houseEntity);
        List<AttachmentEntity> attachmentEntities = request.getAttachments().stream()
                .map(attachmentDTO -> {
                    return AttachmentEntity.builder()
                            .id(attachmentDTO.getAttachmentId())
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

//         then save new images... need change
        housingRepository.save(houseMapper.fromUpdatingToEntity(updatingHouse, request));

//        updateImageToHouse(updatingHouse.getAttachmentEntities(), updatingHouse);
    }

    @Override
    @Transactional
    public HouseResponse getHouseBySlug(String slug) throws Exception {
        var result = housingRepository.findBySlug(slug)
                .orElseThrow(() -> new Exception(String.format("House with slug %s not found", slug)));
        result.setTotalViews(result.getTotalViews()+1);
        housingRepository.save(result);
        var attachments = result.getAttachments();
        var poster = userClient.fetchUser(result.getPosterId());
        var response = houseMapper.toDto(result);
        response.setPoster(poster.getData());
        response.setAttachments(attachmentMapper.toDto(attachments));
        return response;
    }

    @Override
    public PageResponse<HouseResponse> findMyHouse(UserDTO userRequest, Pageable pageable) {
        var result = housingRepository.findAllByPosterId(userRequest.getUserId(),pageable);
        var totalViews = housingRepository.findTotalViewsByPosterId(userRequest.getUserId());
        return PageResponse.<HouseResponse>builder()
                .totalElements((int) result.getTotalElements())
                .totalViews(totalViews)
                .currentPage(result.getNumber())
                .totalPages(result.getTotalPages())
                .pageSize(result.getSize())
                .hasPrevious(result.hasPrevious())
                .hasNext(result.hasNext())
                .data(result.getContent().stream().map(houseEntity -> {
                    return houseMapper.toDto(houseEntity);
                }).collect(Collectors.toList()))
                .build();
    }


    @Override
    public PageResponse<HouseResponse> findTopFavorite(Pageable pageable) {
        var result = housingRepository.findTopFavorite(pageable);
        var poster = mapPostersById(result.stream().map(HouseEntity::getPosterId).collect(Collectors.toList()));
        return PageResponse.<HouseResponse>builder()
                .currentPage(result.getNumber())
                .totalPages(result.getTotalPages())
                .pageSize(result.getSize())
                .hasPrevious(result.hasPrevious())
                .hasNext(result.hasNext())
                .data(result.getContent().stream().map(houseEntity -> {
                    HouseResponse houseResponse = houseMapper.toDto(houseEntity);
                    houseResponse.setPoster(poster.get(houseEntity.getPosterId()));
                    return houseResponse;
                }).collect(Collectors.toList()))
                .build();
    }
    @Override
    public PageResponse<HouseResponse> findAllVerified(Pageable pageable) {
        var result = housingRepository.findAllVerified(pageable);
        var poster = mapPostersById(result.stream().map(HouseEntity::getPosterId).collect(Collectors.toList()));
        return PageResponse.<HouseResponse>builder()
                .currentPage(result.getNumber())
                .totalPages(result.getTotalPages())
                .pageSize(result.getSize())
                .hasPrevious(result.hasPrevious())
                .hasNext(result.hasNext())
                .data(result.getContent().stream().map(houseEntity -> {
                    HouseResponse houseResponse = houseMapper.toDto(houseEntity);
                    houseResponse.setPoster(poster.get(houseEntity.getPosterId()));
                    return houseResponse;
                }).collect(Collectors.toList()))
                .build();
    }

    @Override
    public PageResponse<HouseResponse> findHouse(HouseSearchRequest request) {
        var result = housingRepository.findAll(request.specification(),request.getPaging().pageable());
        var poster = mapPostersById(result.stream().map(HouseEntity::getPosterId).collect(Collectors.toList()));
        return PageResponse.<HouseResponse>builder()
                .totalElements((int) result.getTotalElements())
                .currentPage(result.getNumber())
                .totalPages(result.getTotalPages())
                .pageSize(result.getSize())
                .hasPrevious(result.hasPrevious())
                .hasNext(result.hasNext())
                .data(result.getContent().stream().map(houseEntity -> {
                    HouseResponse houseResponse = houseMapper.toDto(houseEntity);
                    houseResponse.setPoster(poster.get(houseEntity.getPosterId()));
                    return houseResponse;
                }).collect(Collectors.toList()))
                .build();
    }

    private Map<Long,UserDTO> mapPostersById(List<Long> Ids){
        Set<Long> uniqueIds = new HashSet<>(Ids);
        try {
            var posters = userClient.fetchUsers(new ArrayList<>(uniqueIds));
            return posters.getData().stream()
                    .collect(Collectors.toMap(UserDTO::getUserId, Function.identity()));
        } catch (Exception e) {
            String errorMessage = String.format("Error fetching users with IDs: %s. Error message: %s",
                    uniqueIds.toString(), e.getMessage());
            throw new HousingException("A-01");
        }
    }
}
