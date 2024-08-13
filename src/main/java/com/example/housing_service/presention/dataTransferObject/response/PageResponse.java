package com.example.housing_service.presention.dataTransferObject.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.core.serializer.Serializer;

import java.io.Serializable;
import java.util.List;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageResponse<T> implements Serializable {
    Integer total;
    Integer currentPage;
    Integer pageSize;
    List<T> data;
}
