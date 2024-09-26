package com.carrefour.kata.services.mapper;

import com.carrefour.kata.domains.Cart;
import com.carrefour.kata.services.dto.CartDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring" , uses = {CartItemMapper.class})
public non-sealed interface CartMapper extends EntityMapper<CartDto, Cart> {

    @Override
    CartDto toDto(Cart cart);

    @Override
    Cart toEntity(CartDto cartDto);

    default Cart fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cart cart = new Cart();
        cart.setId(id);
        return cart;
    }
}
