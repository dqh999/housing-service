package com.example.housing_service.presention.controller;

import com.example.housing_service.presention.dataTransferObject.UserDTO;
import com.example.housing_service.presention.dataTransferObject.request.CreationHousingRequest;
import com.example.housing_service.presention.dataTransferObject.request.HousePositionRequest;
import com.example.housing_service.presention.dataTransferObject.request.UpdateHousingRequest;
import com.example.housing_service.presention.dataTransferObject.response.ApiResponse;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
        return ApiResponse.build()
                .withData(housingService.createHousing(userRequest, request))
                .toEntity();
    }
    @PostMapping("/uploadHouses")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> uploadHouses(
            @AuthenticationPrincipal UserDTO userRequest,
            @Valid @RequestBody List<CreationHousingRequest> requests){
        log.info("request size: " + requests.size());
        return ApiResponse.build()
                .withData(requests.stream().map(request -> {
                    return housingService.createHousing(userRequest, request);
                }).collect(Collectors.toList()))
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
    public ResponseEntity<?> getHouseById(@RequestParam Long houseId) throws Exception{
        var result = housingService.getHouseById(houseId);
        return ApiResponse.build()
                .withData(result)
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
                    .withSuccess(false)
                    .withMessage(e.getMessage())
                    .toEntity();
        }
    }
    @PostMapping("/addFavorite")
    public ResponseEntity<?> addFavoriteHouse(@AuthenticationPrincipal UserDTO userRequest,
                                              @RequestParam Long houseId) {
        housingFavoriteService.addFavoriteHouse(userRequest, houseId);
        return ApiResponse.build()
                .withCode(200)
                .withMessage("House has been added to favorites")
                .toEntity();
    }
    @GetMapping("/favorite")
    public ResponseEntity<?> getTopFavorite(@RequestParam(defaultValue = "10") int limit,
                                            @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, limit);
        var result = housingService.findTopFavorite(pageable);
        return ApiResponse.build()
                .withData(result)
                .toEntity();
    }
}
