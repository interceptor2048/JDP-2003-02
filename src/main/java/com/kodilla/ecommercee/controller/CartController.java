package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.ProductDto;
import com.kodilla.ecommercee.mapper.CartMapper;
import com.kodilla.ecommercee.service.CartDbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/carts")
public class CartController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);
    private CartMapper cartMapper;
    private CartDbService cartDbService;

    @Autowired
    public CartController(CartMapper cartMapper, CartDbService cartDbService) {
        this.cartMapper = cartMapper;
        this.cartDbService = cartDbService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void create(@RequestBody Long userId) {
        cartDbService.saveCart(cartMapper.mapToCart(cartDbService.createEmptyCart(userId)));
    }

    @GetMapping("/{cartId}")
    public List<ProductDto> get(@PathVariable Long cartId) {
        return cartDbService.getProductsFormBasket(cartId);
    }

    @PutMapping("/{cartId}")
    public void addProduct(@PathVariable Long cartId, @RequestBody ProductDto productDto) {
        cartDbService.addItemToCart(cartId, productDto);
    }

    @DeleteMapping("{cartId}/{productId}")
    public void removeProduct(@PathVariable Long cartId, @PathVariable Long productId) {

        if (cartDbService.removeItemsFromCart(cartId, productId)) {
            LOGGER.info("Product has been successfully removed from Basket");
        }
    }

    @PostMapping("{cartId}/createOrder")
    public void createOrder(@PathVariable Long cartId) {
        cartDbService.createOrderFromCart(cartId);
    }
}
