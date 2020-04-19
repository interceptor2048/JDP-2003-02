package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.CartDto;
import com.kodilla.ecommercee.domain.ProductDto;
import com.kodilla.ecommercee.mapper.CartMapper;
import com.kodilla.ecommercee.mapper.ProductMapper;
import com.kodilla.ecommercee.service.CartDbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

;


@RestController
@RequestMapping("/v1/carts")
public class CartController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);
    private CartMapper cartMapper;
    private CartDbService cartDbService;
    private ProductMapper productMapper;

    @Autowired
    public CartController(CartMapper cartMapper, CartDbService cartDbService, ProductMapper productMapper) {
        this.cartMapper = cartMapper;
        this.cartDbService = cartDbService;
        this.productMapper = productMapper;
    }

    @PostMapping("/{id}")
    public void create(@PathVariable Long userId) {
        List<ProductDto> items = new ArrayList<>();
        CartDto cartDto = CartDto.builder().id(null).totalPrice(new BigDecimal(0.0)).isClosed(false)
                .userId(userId).cartItems(items).build();
        cartDbService.saveCart(cartMapper.mapToCart(cartDto));
    }

    @GetMapping("{id}/products")
    public List<ProductDto> getProducts(@PathVariable Long cartId) {
        List<ProductDto> actualCart = new ArrayList<>();
        Optional<Cart> readCart = Optional.ofNullable(cartDbService.getCartById(cartId).orElseThrow(() -> new RuntimeException("Cart not found")));
        if (readCart.isPresent()) {
            CartDto cartDto = cartMapper.mapToCartDto(readCart.get());
            actualCart = cartDto.getCartItems();
        }
        return actualCart;
    }

    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public void addProduct(@PathVariable Long cartId, @RequestBody ProductDto productDto) {
        cartDbService.addItemToCart(cartId, productMapper.mapToProduct(productDto));
    }

    @DeleteMapping("{id}/{productId}")
    public void removeProduct(@PathVariable Long cartId, @PathVariable Long productId) {

        if (cartDbService.removeItemsFromCart(cartId, productId)) {
            LOGGER.info("Product has been successfully removed from Cart");
        }
    }

    @PostMapping("{id}/createOrder")
    public void createOrder(@PathVariable Long cartId) {
        cartDbService.createOrderFromCart(cartId);
    }
}
