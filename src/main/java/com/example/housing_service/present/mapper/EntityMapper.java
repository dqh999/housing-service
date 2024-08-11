package com.example.housing_service.present.mapper;

import org.mapstruct.*;

import java.util.List;

/**
 * E means entity,
 * D means dto,
 * this interface automatically map entity to dto with common functionality
 * */
public interface EntityMapper<E, D> {
    D toDto(E e);

    E toEntity(D d);

    List<D> toDto(List<E> e);

    List<E> toEntity(List<D> d);

    @Named("partialUpdate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "updated_at", ignore = true)
    void partialUpdate(@MappingTarget E entity, D dto);
}
