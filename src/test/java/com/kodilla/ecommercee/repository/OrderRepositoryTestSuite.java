package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.repository.OrderRepository;
import com.kodilla.ecommercee.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderRepositoryTestSuite {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    private Order getTestOrder() {
        List<Order> orders1 = new ArrayList<>();
        List<Order> orders2 = new ArrayList<>();
        List<Order> orders3 = new ArrayList<>();
        User user = User.builder().id(null).username("Jonny").build();
        Product product1 = Product.builder().id(null).name("jacket").orders(orders1).build();
        Product product2 = Product.builder().id(null).name("toys").orders(orders2).build();
        Product product3 = Product.builder().id(null).name("plates").orders(orders3).build();
        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        products.add(product3);

        return Order.builder().id(null).creationDate(LocalDate.now()).status("open").user(user).products(products).build();
    }

    @Test
    public void testSaveOrder() {
        //Given
        Order order = getTestOrder();
        User user = order.getUser();

        //When
        userRepository.save(user);
        orderRepository.save(order);

        //Then
        Long orderId = order.getId();
        Optional<Order> actualOrder = orderRepository.findById(orderId);
        Assert.assertTrue(actualOrder.isPresent());
        int actualOrderSize = actualOrder.get().getProducts().size();
        Assert.assertEquals(3, actualOrderSize);
        Assert.assertEquals("Jonny", actualOrder.get().getUser().getUsername());

        //CleanUp
        orderRepository.delete(actualOrder.get());
        userRepository.delete(user);
    }

    @Test
    public void testUpdateOrder() {
        //Given
        Order order = getTestOrder();
        User user = order.getUser();

        //When
        userRepository.save(user);
        orderRepository.save(order);
        order.setStatus("closed");

        //Then
        Long orderId = order.getId();
        Optional<Order> actualOrder = orderRepository.findById(orderId);
        Assert.assertTrue(actualOrder.isPresent());
        String orderStatus = actualOrder.get().getStatus();
        Assert.assertEquals("closed", orderStatus);

        //CleanUp
        orderRepository.delete(actualOrder.get());
        userRepository.delete(user);
    }

    @Test
    public void testDeleteOrder() {
        //Given
        Order order = getTestOrder();
        User user = order.getUser();

        //When
        userRepository.save(user);
        orderRepository.save(order);
        Long orderId = order.getId();
        orderRepository.delete(order);

        //Then
        Optional<Order> actualOrder = orderRepository.findById(orderId);
        Assert.assertFalse(actualOrder.isPresent());

        //CleanUp
        userRepository.delete(user);
    }
}

