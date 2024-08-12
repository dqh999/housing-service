package com.example.housing_service.presention.dataTransferObject.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageResponse<T> {
    Integer total;
    Integer currentPage;
    Integer pageSize;
    T data;
}
