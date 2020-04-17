package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.*;
import com.kodilla.ecommercee.mapper.CartMapper;
import com.kodilla.ecommercee.mapper.ProductMapper;
import com.kodilla.ecommercee.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class CartDbService {

    private CartRepository cartRepository;
    private CartMapper cartMapper;
    private OrderDbService orderDbService;
    private ProductMapper productMapper;

    @Autowired
    public CartDbService(CartRepository cartRepository, CartMapper cartMapper,
                         OrderDbService orderDbService, ProductMapper productMapper) {
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
        this.orderDbService = orderDbService;
        this.productMapper = productMapper;
    }

    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }

    public Optional<Cart> getCartById(Long cartId) {
        return cartRepository.findById(cartId);
    }

    public List<Cart> getCarts() {
        return cartRepository.findAll();
    }

    public CartDto createEmptyCart(Long userId) {
        List<ProductDto> items = new ArrayList<>();
        return CartDto.builder().id(null).totalPrice(new BigDecimal(0.0)).isClosed(false)
                .userId(userId).cartItems(items).build();
    }

    public List<ProductDto> getProductsFormBasket(Long cartId) {
        List<ProductDto> actualBasket = new ArrayList<>();
        Optional<Cart> readCart = Optional.ofNullable(getCartById(cartId).orElseThrow(() -> new RuntimeException("Cart not found")));
        if (readCart.isPresent()) {
            CartDto cartDto = cartMapper.mapToCartDto(readCart.get());
            actualBasket = cartDto.getCartItems();
        }
        return actualBasket;
    }

    public void createOrderFromCart(Long cartId) {
        Optional<Cart> readCart = Optional.ofNullable(getCartById(cartId).orElseThrow(() -> new RuntimeException("Cart not found")));
        if (readCart.isPresent()) {
            LocalDate actualTime = LocalDate.now();
            Order order = Order.builder().id(null).creationDate(actualTime).user(readCart.get().getUser())
                    .products(readCart.get().getCartItems()).status("Open").build();
            orderDbService.saveOrder(order);
            readCart.get().setClosed(true);
            saveCart(readCart.get());
        }
    }

    public boolean removeItemsFromCart(Long cartId, Long productId) {
        List<Cart> carts = getCarts();
        Iterator<Cart> cartIterator = carts.stream()
                .filter(c -> c.getId().equals(cartId))
                .iterator();

        while (cartIterator.hasNext()) {
            Cart readCart = cartIterator.next();
            int cartSize = readCart.getCartItems().size();
            for (int n = 0; n < cartSize; n++) {
                if (readCart.getCartItems().get(n).getId().equals(productId)) {
                    readCart.getCartItems().remove(n);
                    BigDecimal updatePrice = readCart.getTotalPrice().subtract(readCart.getCartItems().get(n).getPrice());
                    readCart.setTotalPrice(updatePrice);
                    saveCart(readCart);
                    return true;
                }
            }
        }
        return false;
    }

    public void addItemToCart(Long cartId, ProductDto productDto) {
        Optional<Cart> readCart = Optional.ofNullable(getCartById(cartId).orElseThrow(() -> new RuntimeException("Cart not found")));
        if (readCart.isPresent()) {
            if (!readCart.get().isClosed()) {
                Product product = productMapper.mapToProduct(productDto);
                readCart.get().getCartItems().add(product);
                BigDecimal updateTotalPrice = readCart.get().getTotalPrice().add(product.getPrice());
                readCart.get().setTotalPrice(updateTotalPrice);
                saveCart(readCart.get());
            }
        }
    }
}
