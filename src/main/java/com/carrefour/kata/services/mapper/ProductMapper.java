package com.carrefour.kata.services.mapper;

import com.carrefour.kata.domains.Product;
import com.carrefour.kata.services.dto.ProductDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public non-sealed interface ProductMapper extends EntityMapper<ProductDto, Product> {

    @Override
    ProductDto toDto(Product product);

    @Override
    Product toEntity(ProductDto productDto);

    default Product fromId(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }
}
