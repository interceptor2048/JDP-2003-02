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

    @PostMapping(value="createCart",consumes = APPLICATION_JSON_VALUE)
    public void createCart(@RequestParam Long userId) {
        cartDbService.saveCart(cartMapper.mapToCart(cartDbService.createEmptyCart(userId)));
    }

    @GetMapping(value="getProductFromCart")
    public List<ProductDto> getPrductFromCart(@RequestParam Long cartId) {
        return cartDbService.getProductsFormBasket(cartId);
    }

    @PutMapping(value="addProduct")
    public void addProduct(@RequestParam Long cartId, @RequestBody ProductDto productDto) {
        cartDbService.addItemToCart(cartId, productDto);
    }

    @DeleteMapping(value="removeProduct")
    public void removeProduct(@RequestParam Long cartId, @RequestParam Long productId) {

        if (cartDbService.removeItemsFromCart(cartId, productId)) {
            LOGGER.info("Product has been successfully removed from Basket");
        }
    }

    @PostMapping(value="createOrder")
    public void createOrder(@RequestParam Long cartId) {
        cartDbService.createOrderFromCart(cartId);
    }
}
