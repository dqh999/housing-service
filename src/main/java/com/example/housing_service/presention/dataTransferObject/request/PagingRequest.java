package com.example.housing_service.presention.dataTransferObject.request;

import com.example.housing_service.persistence.repository.specification.HouseSpecification;
import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter @Setter  @NoArgsConstructor @AllArgsConstructor
@Builder
public class PagingRequest {
    private int page;
    private int size;
    private Map<String, String> orders = new HashMap<>();

    public Pageable pageable() {
        if (page<1) page = 1;
        if (CollectionUtils.isEmpty(orders)) {
            return PageRequest.of(page - 1, size);
        }
        Sort sortable = sortable(orders);
        return PageRequest.of(page - 1, size, sortable);
    }

    public Sort sortable(Map<String, String> orders) {
        List<Sort.Order> sortableList = new ArrayList<>();
        orders.forEach((key, value) -> {
            if (HouseSpecification.isFieldExists(key)){
                Sort.Direction direction = Sort.Direction.DESC.name().equals(value.toUpperCase()) ? Sort.Direction.DESC : Sort.Direction.ASC;
                Sort.Order order = new Sort.Order(direction, key);
                sortableList.add(order);
            }
        });
        return Sort.by(sortableList);

    }
    public static Map<String, String> extractSortingParams (String sort){
        Map<String, String> sortingParams = new HashMap<>();
        if (sort != null && !sort.isEmpty()) {
            String[] sortParts = sort.split("-");
            if (sortParts.length == 2) {
                String field = sortParts[0];
                String order = sortParts[1];
                sortingParams.put(field, order);
            }
        }
        return sortingParams;
    }
}
