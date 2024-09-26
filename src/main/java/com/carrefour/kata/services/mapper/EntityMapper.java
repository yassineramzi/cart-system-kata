package com.carrefour.kata.services.mapper;

import java.util.List;

/**
 * Contract for a generic dto to entity mapper.
 *
 * @param <D> - DTO type parameter.
 * @param <E> - Entity type parameter.
 */

public sealed interface EntityMapper<D, E> permits CustomerMapper, CartMapper, CartItemMapper, ProductMapper, OrderMapper, OrderItemMapper {

    E toEntity(D dto);

    D toDto(E entity);

    List<E> toEntity(List<D> dtoList);

    List<D> toDto(List<E> entityList);
}
