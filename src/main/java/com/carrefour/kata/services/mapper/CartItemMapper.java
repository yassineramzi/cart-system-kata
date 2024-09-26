package com.carrefour.kata.services.mapper;

import com.carrefour.kata.domains.CartItem;
import com.carrefour.kata.services.dto.CartItemDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public non-sealed interface CartItemMapper extends EntityMapper<CartItemDto, CartItem> {

    @Override
    CartItemDto toDto(CartItem cartItem);

    @Override
    CartItem toEntity(CartItemDto cartItemDto);

    default CartItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        CartItem cartItem = new CartItem();
        cartItem.setId(id);
        return cartItem;
    }
}
