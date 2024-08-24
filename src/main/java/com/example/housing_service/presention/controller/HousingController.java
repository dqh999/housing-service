package com.example.housing_service.presention.controller;

import com.example.housing_service.presention.dataTransferObject.UserDTO;
import com.example.housing_service.presention.dataTransferObject.request.*;
import com.example.housing_service.presention.dataTransferObject.response.ApiResponse;
import com.example.housing_service.presention.dataTransferObject.response.ResponseCode;
import com.example.housing_service.presention.exception.BusinessException;
import com.example.housing_service.presention.service.HousingFavoriteService;
import com.example.housing_service.presention.service.HousingService;
import com.example.housing_service.util.ValidationUtil;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/${api.prefix}/housing")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true) @Slf4j
public class HousingController {
    HousingService housingService;
    HousingFavoriteService housingFavoriteService;
    @PostMapping("/uploadHouse")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> createHouse(
            @AuthenticationPrincipal UserDTO userRequest,
            @Valid @RequestBody CreationHousingRequest request){
        var result = housingService.createHousing(userRequest, request);
        return ApiResponse.build()
                .withData(result)
                .toEntity();
    }

    @PutMapping("/{houseId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> updateHouse(@PathVariable Long houseId, @Valid @RequestBody UpdateHousingRequest request) throws Exception {
        housingService.updateHousing(houseId, request);
        return ApiResponse.build()
                .withMessage("success")
                .toEntity();
    }
    @GetMapping
    public ResponseEntity<?> getHouseBySlug(@RequestParam String slug){
        var result = housingService.getHouseBySlug(slug);
        return ApiResponse.build()
                .withData(result)
                .toEntity();
    }
    @GetMapping("/search")
    public ResponseEntity<?> searchHouse(@RequestParam(required = false) Long posterId,
                                        @RequestParam(required = false) String keyword,

                                         @RequestParam(required = false) String roomType,
                                         @RequestParam(required = false) String roomCategory,

                                         @RequestParam(required = false) Double priceMin,
                                         @RequestParam(required = false) Double priceMax,

                                         @RequestParam(required = false) String address,
                                         @RequestParam(required = false) Double latitude,
                                         @RequestParam(required = false) Double longitude,
                                         @RequestParam(required = false) Integer radius,

                                         @RequestParam Map<String, String> featureFlags,
                                         @RequestParam(defaultValue = "10") int limit,
                                         @RequestParam(defaultValue = "1") int page,
                                         @RequestParam(required = false) String sort){
        Map<String, Boolean> booleanFeatureFlags = featureFlags.entrySet().stream()
                .filter(entry -> "true".equalsIgnoreCase(entry.getValue()) || "false".equalsIgnoreCase(entry.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> Boolean.parseBoolean(entry.getValue())
                ));
        HouseSearchRequest request = HouseSearchRequest.builder()
                .posterId(posterId)
                .keyword(keyword)
                .roomType(roomType)
                .roomCategory(roomCategory)
                .minPrice(priceMin)
                .maxPrice(priceMax)
                .address(address)
                .latitude(latitude)
                .longitude(longitude)
                .radius(radius)
                .featureFlags(booleanFeatureFlags)
                .paging(PagingRequest.builder().page(page).size(limit).orders(PagingRequest.extractSortingParams(sort)).build())
                .build();
        var result = housingService.findHouse(request);
        return ApiResponse.build()
                .withCode(200)
                .withData(result)
                .toEntity();
    };
    @GetMapping("/myHouse")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> getAllMyHouse(@AuthenticationPrincipal UserDTO userRequest,
                                               @RequestParam(defaultValue = "10") int limit,
                                               @RequestParam(defaultValue = "1") int page) {
        Pageable pageable = PageRequest.of(page-1,limit, Sort.by(Sort.Direction.DESC, "updatedAt"));
        var result = housingService.findMyHouse(userRequest,pageable);
        return ApiResponse.build()
                .withCode(200)
                .withData(result)
                .toEntity();
    }
    @GetMapping("/favorite")
    public ResponseEntity<?> getTopFavorite(@RequestParam(defaultValue = "10") int limit,
                                            @RequestParam(defaultValue = "1") int page) {
        Pageable pageable = PageRequest.of(page-1, limit);
        var result = housingService.findTopFavorite(pageable);
        return ApiResponse.build()
                .withData(result)
                .toEntity();
    }
    @GetMapping("/verified")
    public ResponseEntity<?> getAllVerified(@RequestParam(defaultValue = "10") int limit,
                                            @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, limit);
        var result = housingService.findAllVerified(pageable);
        return ApiResponse.build()
                .withData(result)
                .toEntity();
    }
    @PostMapping("/addFavorite")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> addFavoriteHouse(@AuthenticationPrincipal UserDTO userRequest,
                                              @RequestParam Long houseId) {
        housingFavoriteService.addFavoriteHouse(userRequest, houseId);
        return ApiResponse.build()
                .withCode(200)
                .withMessage("House has been added to favorites")
                .toEntity();
    }
    @PostMapping("/removeFavorite")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> removeFavorite(@AuthenticationPrincipal UserDTO userRequest,
                                              @RequestParam Long houseId) {
        housingFavoriteService.addFavoriteHouse(userRequest, houseId);
        return ApiResponse.build()
                .withCode(200)
                .withMessage("House has been added to favorites")
                .toEntity();
    }
}
