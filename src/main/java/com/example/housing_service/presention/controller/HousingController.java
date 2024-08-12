package com.example.housing_service.presention.controller;

import com.example.housing_service.presention.dataTransferObject.request.CreationHousingRequest;
import com.example.housing_service.presention.dataTransferObject.request.UpdateHousingRequest;
import com.example.housing_service.presention.dataTransferObject.response.ApiResponse;
import com.example.housing_service.presention.service.HousingService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/housing")
public class HousingController{
    HousingService housingService;

    @PostMapping
    public ResponseEntity<?> addHouseToUser(@Valid @RequestBody CreationHousingRequest request){
        return ApiResponse.build()
                .withData(housingService.createHousing(request))
                .toEntity();
    }

    @GetMapping
    public ResponseEntity<?> getAllHouses(){
        return ApiResponse.build()
                .withData(housingService.findAll())
                .toEntity();
    }

    @PutMapping("/{houseId}")
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
}
