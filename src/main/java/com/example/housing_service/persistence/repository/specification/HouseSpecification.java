package com.example.housing_service.persistence.repository.specification;

import com.example.housing_service.persistence.model.house.HouseEntity;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HouseSpecification {

    private final List<Specification<HouseEntity>> specifications = new ArrayList<>();


    public static HouseSpecification builder() {
        return new HouseSpecification();
    }


    public static Specification<HouseEntity> hasField(String fieldName, Object value) {
        if (value != null ) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(fieldName), value);
        }
        return null;
    }
    public static Specification<HouseEntity> hasFieldLike(String fieldName, String value) {
        if (value != null && !value.trim().isEmpty()) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.upper(root.get(fieldName)), like(value));
        }
        return null;
    }
    public static Specification<HouseEntity> withLocation(final double longitude,final double latitude, final double radius) {
        return (root, query, criteriaBuilder) -> {
            String location = "POINT(" + longitude + " " + latitude + ")";

            jakarta.persistence.criteria.Expression<Double> distance = criteriaBuilder.function(
                    "ST_Distance_Sphere", Double.class,
                    root.get("geom"),
                    criteriaBuilder.function("ST_GeomFromText", String.class, criteriaBuilder.literal(location))
            );
            Predicate withinRadius = criteriaBuilder.lessThanOrEqualTo(distance, radius);
            if (distance != null) {
                query.orderBy(criteriaBuilder.asc(distance));
            }

            return withinRadius;
        };
    }

    private static String like(final String value) {
        return "%" + value.toUpperCase() + "%";
    }

    public Specification<HouseEntity> build() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(specifications.stream()
                .filter(Objects::nonNull)
                .map(s -> s.toPredicate(root, query, criteriaBuilder)).toArray(Predicate[]::new));
    }
    public static boolean isFieldExists(String fieldName) {
        try {
            Field field = HouseEntity.class.getDeclaredField(fieldName);
            return field != null;
        } catch (NoSuchFieldException e) {
            System.out.println("lỗi nha!");
            return false;
        }
    }
}
