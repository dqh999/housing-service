package com.example.housing_service.persistence.repository;

import com.example.housing_service.persistence.model.house.HouseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HousingRepository extends JpaRepository<HouseEntity, Long>, JpaSpecificationExecutor<HouseEntity> {
    @Query("SELECT h FROM HouseEntity h WHERE h.posterId = :posterId")
    Page<HouseEntity> findAllByPosterId(@Param("posterId") Long posterId, Pageable pageable);
    Optional<HouseEntity> findBySlug(String slug);
//    Page<HouseEntity> findAllByAddressContainingIgnoreCase(String address, Pageable pageable);
//    @Query(value = "SELECT h.* FROM `tbl_houses` h " +
//            "WHERE ST_Distance_Sphere(h.geom, ST_GeomFromText(CONCAT('POINT(', :longitude, ' ', :latitude, ')'))) <= :radius " +
//            "ORDER BY ST_Distance_Sphere(h.geom, ST_GeomFromText(CONCAT('POINT(', :longitude, ' ', :latitude, ')')))",
//            nativeQuery = true)
//    Page<HouseEntity> findAllByDistanceWithin(@Param("longitude") Double longitude,
//                                              @Param("latitude") Double latitude,
//                                              @Param("radius") Integer radius,
//                                              Pageable pageable);
    @Query("SELECT h FROM HouseEntity h " +
            "WHERE h.isVerified = true")
    Page<HouseEntity> findAllVerified(Pageable pageable);
    @Query("SELECT h FROM HouseEntity h " +
            "JOIN HouseFavoriteEntity hf ON h.id = hf.houseId " +
            "GROUP BY h.id " +
            "ORDER BY COUNT(hf) DESC")
    Page<HouseEntity> findTopFavorite(Pageable pageable);
}
