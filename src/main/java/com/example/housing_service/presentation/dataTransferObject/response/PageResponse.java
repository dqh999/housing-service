package com.example.housing_service.presentation.dataTransferObject.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter @Builder
public class PageResponse<T> implements Serializable {
    Integer totalElements;
    Integer totalViews;
    Integer totalPages;
    Integer currentPage;
    Integer pageSize;
    List<T> data;
    Boolean hasNext;
    Boolean hasPrevious;
}