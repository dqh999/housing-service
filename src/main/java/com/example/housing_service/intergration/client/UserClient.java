package com.example.housing_service.intergration.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

import java.awt.*;

@FeignClient(name = "user-service", url = "http://localhost:9000/api/v1/user")
public interface UserClient {
    @PostMapping(value = "/update-image", produces = MediaType.APPLICATION_JSON_VALUE)
    Object  updateImageToUser();
}
