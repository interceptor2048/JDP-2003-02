package com.kodilla.ecommercee.service;


import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.Product;
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
    private OrderDbService orderDbService;

    @Autowired
    public CartDbService(CartRepository cartRepository, OrderDbService orderDbService) {
        this.cartRepository = cartRepository;
        this.orderDbService = orderDbService;

    }

    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public Optional<Cart> getCartById(Long cartId) {
        return cartRepository.findById(cartId);
    }

    public List<Cart> getCarts() {
        return cartRepository.findAll();
    }

    public void createOrderFromCart(Long cartId) {
        Optional<Cart> readCart = Optional.ofNullable(getCartById(cartId).orElseThrow(() -> new RuntimeException("Cart not found")));
        if (readCart.isPresent()) {
            LocalDate actualTime = LocalDate.now();
            Order order = Order.builder().id(null).creationDate(actualTime).user(readCart.get().getUser())
                    .products(new ArrayList<>(readCart.get().getCartItems())).status("Open").build();
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

    public void addItemToCart(Long cartId, Product product) {
        Optional<Cart> readCart = Optional.ofNullable(getCartById(cartId).orElseThrow(() -> new RuntimeException("Cart not found")));
        if (readCart.isPresent()) {
            if (!readCart.get().isClosed()) {
                readCart.get().getCartItems().add(product);
                BigDecimal updateTotalPrice = readCart.get().getTotalPrice().add(product.getPrice());
                readCart.get().setTotalPrice(updateTotalPrice);
                saveCart(readCart.get());
            }
        }
    }
}
