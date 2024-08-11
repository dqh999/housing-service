package com.example.housing_service.persistence.entity.repository;

import com.example.housing_service.persistence.entity.House;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HousingRepository extends JpaRepository<House, Long>, JpaSpecificationExecutor<House> {
    Page<House> findAllByAddressContainingIgnoreCase(String address, Pageable pageable);
}
