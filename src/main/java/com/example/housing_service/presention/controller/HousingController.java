package com.example.housing_service.presention.controller;

import com.example.housing_service.presention.dataTransferObject.request.CreationHousingRequest;
import com.example.housing_service.presention.dataTransferObject.request.HousePositionRequest;
import com.example.housing_service.presention.dataTransferObject.request.UpdateHousingRequest;
import com.example.housing_service.presention.dataTransferObject.request.UserRequest;
import com.example.housing_service.presention.dataTransferObject.response.ApiResponse;
import com.example.housing_service.presention.service.HousingFavoriteService;
import com.example.housing_service.presention.service.HousingService;
import com.example.housing_service.util.ValidationUtil;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/housing")
public class HousingController{
    HousingService housingService;
    HousingFavoriteService housingFavoriteService;

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> createHouse(
            @AuthenticationPrincipal UserRequest userRequest,
            @Valid @RequestBody CreationHousingRequest request){
        return ApiResponse.build()
                .withData(housingService.createHousing(userRequest,request))
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

    @GetMapping("/search")
    public ResponseEntity<?> searchByAddress(@RequestParam String address,@RequestParam Pageable pageable){
        return ApiResponse.build()
                .withData(housingService.findByAddress(address, pageable))
                .toEntity();
    }
    @GetMapping("/location")
    public ResponseEntity<?> searchByLocation(@RequestParam Double latitude,
                                              @RequestParam Double longitude,
                                              @RequestParam Integer radius,
                                              @RequestParam(defaultValue = "10") int limit,
                                              @RequestParam(defaultValue = "0") int page){
        try {
            var request = HousePositionRequest.builder()
                    .latitude(latitude)
                    .longitude(longitude)
                    .radius(radius)
                    .build();
            Map<String, Object> errors = ValidationUtil.validate(request);
            if (!errors.isEmpty()){
                return ApiResponse.build()
                        .withSuccess(false)
                        .withErrors(errors)
                        .toEntity();
            }
            Pageable pageable = PageRequest.of(page, limit);
            var result = housingService.findByPosition(request, pageable);
            return ApiResponse.build()
                    .withData(result)
                    .toEntity();
        } catch (Exception e){
            return ApiResponse.build()
                    .withMessage(e.getMessage())
                    .toEntity();
        }
    }
    @PostMapping("/addFavorite")
    public ResponseEntity<?> addFavoriteHouse(@AuthenticationPrincipal UserRequest userRequest,
                                              @RequestParam Long houseId) {
        housingFavoriteService.addFavoriteHouse(userRequest, houseId);
        return ApiResponse.build()
                .withMessage("House has been added to favorites")
                .toEntity();
    }
}
