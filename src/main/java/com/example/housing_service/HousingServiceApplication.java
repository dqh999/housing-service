package com.example.housing_service;

import com.example.housing_service.util.SlugGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class HousingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HousingServiceApplication.class, args);
	}
}
