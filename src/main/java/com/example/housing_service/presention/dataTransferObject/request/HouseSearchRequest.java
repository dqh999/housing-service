package com.example.housing_service.presention.dataTransferObject.request;

import com.example.housing_service.persistence.model.house.HouseEntity;
import com.example.housing_service.persistence.repository.specification.HouseSpecification;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter @Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HouseSearchRequest extends FilterRequest<HouseEntity> {
    String keyword;
    String roomType;
    String roomCategory;

    String address;
    @Min(value = -90, message = "Latitude must be at least -90")
    @Max(value = 90, message = "Latitude must be at most 90")
    Double latitude;
    @Min(value = -180, message = "Longitude must be at least -180")
    @Max(value = 180, message = "Longitude must be at most 180")
    Double longitude;
    @Min(value = 0, message = "Radius must be at least 0")
    Integer radius;

    Double minPrice;
    Double maxPrice;

    Map<String, Boolean> featureFlags;

    PagingRequest paging;

    @Override
    public Specification<HouseEntity> specification() {
        List<Specification<HouseEntity>> specifications = new ArrayList<>();

        specifications.add(HouseSpecification.hasFieldLike("title",keyword));

        specifications.add(HouseSpecification.hasField("roomType",roomType));
        specifications.add(HouseSpecification.hasField("roomCategory",roomCategory));

        if (minPrice != null && maxPrice != null && minPrice > 0 && maxPrice < 10000000 && minPrice < maxPrice) {
            System.out.println("filter between " + minPrice + "max" + maxPrice);
            specifications.add((root, query, criteriaBuilder) ->
                    criteriaBuilder.between(root.get("price"), minPrice, maxPrice));
        } else if (minPrice != null && minPrice > 0 && minPrice < 10000000) {
            System.out.println("filter min price" + minPrice);
            specifications.add((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
        } else if (maxPrice != null && maxPrice < 10000000 && maxPrice > 0) {
            System.out.println("filter max price" + minPrice);
            specifications.add((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
        }

        if (address != null){
            specifications.add(HouseSpecification.hasFieldLike("address",address));
        } else if (longitude != null && latitude != null && radius != null){
            specifications.add(HouseSpecification.withLocation(longitude,latitude,radius));
        }

        if (featureFlags != null) {
            featureFlags.forEach((key, value) -> {
                System.out.println("key  " + key + "         value " + value);
                specifications.add(HouseSpecification.hasField(key, value));
            });
        }
        return specifications.stream()
                .filter(spec -> spec != null)
                .reduce(Specification::and)
                .orElse(null);
    }
}
