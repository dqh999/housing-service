package com.example.housing_service.intergration.client;

import com.example.housing_service.presention.dataTransferObject.UserDTO;
import com.example.housing_service.presention.dataTransferObject.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "user-service",
        url = "${api.user-service}/api/v1/user")
public interface UserClient {
    @GetMapping(value = "/fetchUser/{userId}")
    ApiResponse<UserDTO> fetchUser(@PathVariable Long userId);
    @PostMapping(value = "/fetchUsers")
    ApiResponse<List<UserDTO>> fetchUsers(@RequestBody List<Long> userIds);
}
