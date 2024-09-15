package com.example.housing_service.presentation.service.impl;

import com.example.housing_service.intergration.client.UserClient;
import com.example.housing_service.persistence.repository.AttachmentRepository;
import com.example.housing_service.persistence.repository.HousingRepository;
import com.example.housing_service.presentation.dataTransferObject.UserDTO;
import com.example.housing_service.presentation.dataTransferObject.request.*;
import com.example.housing_service.presentation.dataTransferObject.response.HouseResponse;
import com.example.housing_service.presentation.dataTransferObject.response.PageResponse;
import com.example.housing_service.presentation.dataTransferObject.response.ResponseCode;
import com.example.housing_service.presentation.exception.BusinessException;
import com.example.housing_service.presentation.service.HousingService;
import com.example.housing_service.persistence.model.house.HouseEntity;
import com.example.housing_service.persistence.model.AttachmentEntity;
import com.example.housing_service.presentation.mapper.HouseMapper;
import com.example.housing_service.presentation.mapper.AttachmentMapper;
import com.example.housing_service.util.HouseStatus;
import com.example.housing_service.util.SlugGenerator;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
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
        try {
            var houseEntity = houseMapper.fromCreationToEntity(request);
            houseEntity.setTotalViews(0);
            houseEntity.setFlagCode((byte) 4);
            houseEntity.setStatus(HouseStatus.APPROVED);
            houseEntity.setIsVerified(false);
            var slug = SlugGenerator.generateUniqueSlug(request.getTitle());
            houseEntity.setSlug(slug);
            houseEntity.setPosterId(userRequest.getUserId());
            housingRepository.save(houseEntity);
            List<AttachmentEntity> attachmentEntities = request.getAttachments().stream()
                    .map(attachmentDTO -> {
                        return AttachmentEntity.builder()
                                .id(attachmentDTO.getAttachmentId())
                                .houseEntity(houseEntity)
                                .attachmentType(attachmentDTO.getType())
                                .attachmentName(attachmentDTO.getAttachmentName())
                                .build();
                    })
                    .collect(Collectors.toList());
            System.out.println("attachment" + request.getAttachments());
            attachmentRepository.saveAll(attachmentEntities);
            var result = houseMapper.toDto(houseEntity);
            result.setAttachments(request.getAttachments().stream().toList());
            return result;
        } catch(Exception e){
            throw new BusinessException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
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
    public void deletHouseById(Long houseId) {
        try {
            housingRepository.deleteById(houseId);
        } catch (Exception e){
            throw new BusinessException();
        }
    }

    @Override
    @Transactional
    public HouseResponse getHouseBySlug(String slug) {
        var result = housingRepository.findBySlug(slug)
                .orElseThrow(() -> new BusinessException(ResponseCode.HOUSE_NOT_FOUND));
        if (!result.getStatus().equals(HouseStatus.APPROVED)){
            throw new BusinessException(ResponseCode.HOUSE_NOT_FOUND);
        }
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
            throw new BusinessException();
        }
    }
}
