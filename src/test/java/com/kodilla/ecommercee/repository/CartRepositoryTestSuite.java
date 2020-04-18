package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.*;
import com.kodilla.ecommercee.service.GroupDbService;
import com.kodilla.ecommercee.service.ProductDbService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartRepositoryTestSuite {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private GroupDbService groupDbService;
    @Autowired
    private ProductDbService productDbService;


    @Test
    public void testSaveCart() {
        //Given
        User kowalski = User.builder().id(null).username("kowalski").status("unblocked").userKey(111L).build();
        String groupName = "clothes";
        List<Product> products = new ArrayList<>();
        Group groupClothes = new Group(null, groupName, products);
        List<Order> orders = new ArrayList<>();
        List<Cart> carts = new ArrayList<>();
        Product product1 = Product.builder().id(null).name("Shoes").description("shoes")
                .group(groupClothes).orders(orders).carts(carts).build();
        Product product2 = Product.builder().id(null).name("Coat").description("coat")
                .group(groupClothes).orders(orders).carts(carts).build();
        Product product3 = Product.builder().id(null).name("pullover").description("pullover")
                .group(groupClothes).orders(orders).carts(carts).build();
        products.add(product1);
        products.add(product2);
        products.add(product3);
        List<Product> cartItems = new ArrayList<>();
        cartItems.add(product1);
        cartItems.add(product2);
        cartItems.add(product3);
        Cart cart = Cart.builder().id(null).totalPrice(new BigDecimal(0))
                .isClosed(true).user(kowalski).cartItems(cartItems).build();


        //when
        userRepository.save(kowalski);
        Long kowalskiId = kowalski.getId();
        groupRepository.save(groupClothes);
        Long groupClothesId = groupClothes.getId();
        productRepository.save(product1);
        Long pro1Id = product1.getId();
        productRepository.save(product2);
        Long pro2Id = product2.getId();
        productRepository.save(product3);
        Long pro3Id = product3.getId();
        cartRepository.save(cart);
        Long cartId = cart.getId();

        //Then
        Optional<Cart> currentCart = cartRepository.findById(cartId);
        Assert.assertTrue(currentCart.isPresent());
        int currentCartSize = currentCart.get().getCartItems().size();
        assertEquals(3, currentCartSize);
        assertEquals("kowalski", currentCart.get().getUser().getUsername());

        //CleanUp
        try {
            productDbService.deleteById(pro1Id);
            productDbService.deleteById(pro2Id);
            productDbService.deleteById(pro3Id);
            groupRepository.deleteById(groupClothesId);
            cartRepository.delete(cart);
            userRepository.delete(kowalski);
        } catch (Exception e) {
            //do nothing
        }
    }


    @Test
    public void testUpdateCartStatus() {
        //Given
        User nowak = User.builder().id(null).username("nowak").status("blocked").userKey(99L).build();
        String groupName = "dresses";
        List<Product> products = new ArrayList<>();
        List<Order> orders = new ArrayList<>();
        List<Cart> carts = new ArrayList<>();
        Group groupClothes = new Group(null, groupName, products);
        Product product1 = Product.builder().id(null).name("Shoes").description("shoes")
                .group(groupClothes).orders(orders).carts(carts).build();
        Product product2 = Product.builder().id(null).name("Coat").description("coat")
                .group(groupClothes).orders(orders).carts(carts).build();
        Product product3 = Product.builder().id(null).name("pullover").description("pullover")
                .group(groupClothes).orders(orders).carts(carts).build();
        products.add(product1);
        products.add(product2);
        products.add(product3);
        List<Product> cartItems = new ArrayList<>();
        cartItems.add(product1);
        cartItems.add(product2);
        cartItems.add(product3);
        Cart cart = Cart.builder().id(null).totalPrice(new BigDecimal(20.0))
                .isClosed(false).user(nowak).cartItems(cartItems).build();

        //when
        userRepository.save(nowak);
        Long nowakId = nowak.getId();
        groupRepository.save(groupClothes);
        Long dressesId = groupClothes.getId();
        productRepository.save(product1);
        Long pro1Id = product1.getId();
        productRepository.save(product2);
        Long pro2Id = product2.getId();
        productRepository.save(product3);
        Long pro3Id = product3.getId();
        cartRepository.save(cart);
        Long cartId = cart.getId();

        cart.setClosed(true);
        cartRepository.save(cart);
        Optional<Cart> currentCart = cartRepository.findById(cartId);
        Assert.assertTrue(currentCart.isPresent());
        boolean isReadyToOrder = currentCart.get().isClosed();

        //Then
        Assert.assertTrue(isReadyToOrder);

        //CleanUp
        try {
            userRepository.deleteById(nowakId);
            productDbService.deleteById(pro1Id);
            productDbService.deleteById(pro2Id);
            productDbService.deleteById(pro3Id);
            groupRepository.deleteById(dressesId);
            cartRepository.deleteById(cartId);
        } catch (Exception e) {
            //do nothing
        }
    }
}
