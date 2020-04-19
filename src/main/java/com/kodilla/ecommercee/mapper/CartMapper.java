package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.CartDto;
import com.kodilla.ecommercee.service.ProductDbService;
import com.kodilla.ecommercee.service.UserDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartMapper {

    private ProductMapper productMapper;
    private UserDbService userDbService;
    private ProductDbService productDbService;

    @Autowired
    public CartMapper(ProductMapper productMapper, UserDbService userDbService, ProductDbService productDbService) {
        this.productMapper = productMapper;
        this.userDbService = userDbService;
        this.productDbService = productDbService;
    }

    public Cart mapToCart(final CartDto cartDto) {
        return new Cart(cartDto.getId(),
                cartDto.getTotalPrice(),
                cartDto.isClosed(),
                userDbService.getActualUser(cartDto.getUserId()).get(),
                productMapper.mapToProductList(cartDto.getCartItems()));
    }

    public CartDto mapToCartDto(final Cart cart) {
        return new CartDto(cart.getId(),
                cart.getTotalPrice(),
                cart.isClosed(),
                cart.getUser().getId(),
                productMapper.mapToProductDtoList(cart.getCartItems()));
    }
}
