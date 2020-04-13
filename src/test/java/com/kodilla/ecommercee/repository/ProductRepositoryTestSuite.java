/*
package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.exceptions.GroupNotFoundException;
import com.kodilla.ecommercee.exceptions.ProductNotFoundException;
import com.kodilla.ecommercee.service.GroupDbService;
import com.kodilla.ecommercee.service.ProductDbService;
import org.junit.Before;
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
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductRepositoryTestSuite {

    @Autowired
    private ProductDbService productDbService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private GroupDbService groupDbService;

    private Group group;
    private List<Product> productList;
    private List<Order>


    @Before
    public void setUp() {

    }


    private void createData() {
        List<Product> productList = new ArrayList<>();
        clothes = new Group(null, "clothes", productList);
        Product cos = new Product()
        trousers = new Product(null, "Trousers", "Denim", new BigDecimal("31"), clothes);
        boots = new Product(null, "Boots", "Leather", new BigDecimal("43.2"), clothes);
        shirt = new Product(null, "shirt", "White", new BigDecimal("43.2"), clothes);
        productList.add(trousers);
        productList.add(boots);
        productList.add(shirt);
    }


    @Test
    public void testSaveProduct() {
        //Given
        createData();

        //When
        groupRepository.save(clothes);
        productRepository.save(trousers);
        productRepository.save(boots);
        productRepository.save(shirt);

        //Then
        Long trousersId = trousers.getId();
        Long bootsId = boots.getId();
        Long shirtId = shirt.getId();
        Long clothesId = clothes.getId();
        Optional<Product> actualTrousers = productRepository.findById(trousersId);
        Optional<Product> actualBoots = productRepository.findById(bootsId);
        Optional<Product> actualShirt = productRepository.findById(shirtId);
        Optional<Group> actualClothes = groupRepository.findById(clothesId);

        assertTrue(actualClothes.isPresent());
        assertTrue(actualTrousers.isPresent());
        assertTrue(actualBoots.isPresent());
        assertTrue(actualShirt.isPresent());
        assertEquals(3, actualClothes.get().getProducts().size());
        assertEquals(Product.class, actualClothes.get().getProducts().get(0).getClass());
        assertTrue(actualClothes.get().getProducts().stream()
                .anyMatch(e -> e.getName().equals(actualBoots.get().getName())));
        assertTrue(actualClothes.get().getProducts().stream()
                .anyMatch(e -> e.getDescription().equals(actualTrousers.get().getDescription())));
        assertTrue(actualClothes.get().getProducts().stream()
                .anyMatch(e -> e.getPrice().equals(actualShirt.get().getPrice())));

        //CleanUp
        productDbService.deleteById(trousersId);
        productDbService.deleteById(bootsId);
        productDbService.deleteById(shirtId);
        groupDbService.deleteById(clothesId);
    }

    @Test
    public void testDeleteProduct() throws GroupNotFoundException {
        //Given
        createData();

        //When
        groupRepository.save(clothes);
        productRepository.save(trousers);
        productRepository.save(boots);
        productRepository.save(shirt);
        //Then
        Long trousersId = trousers.getId();
        Long bootsId = boots.getId();
        Long shirtId = shirt.getId();
        Long clothesId = clothes.getId();
        Optional<Product> actualShirt = productRepository.findById(shirtId);
        Optional<Group> actualClothes = groupRepository.findById(clothesId);

        int sizeOfGroup = actualClothes.orElseThrow(GroupNotFoundException::new).getProducts().size();

        productDbService.deleteById(trousersId);
        actualClothes = groupRepository.findById(clothesId);
        int sizeOfGroupAfterDeleting = actualClothes.orElseThrow(GroupNotFoundException::new).getProducts().size();


        assertTrue(actualShirt.isPresent());
        assertEquals(3, sizeOfGroup);
        assertEquals(2, sizeOfGroupAfterDeleting);

        //CleanUp
        productDbService.deleteById(bootsId);
        productDbService.deleteById(shirtId);
        groupDbService.deleteById(clothesId);
    }

    @Test
    public void testProductUpdate() throws ProductNotFoundException {
        //Given
        createData();

        //When
        groupRepository.save(clothes);
        productRepository.save(trousers);
        productRepository.save(boots);
        productRepository.save(shirt);

        //Then
        Long trousersId = trousers.getId();
        Long bootsId = boots.getId();
        Long shirtId = shirt.getId();
        Long clothesId = clothes.getId();

        Optional<Product> actualTrousers = productRepository.findById(trousersId);
        Optional<Product> actualBoots = productRepository.findById(bootsId);
        Optional<Product> actualShirt = productRepository.findById(shirtId);

        String newTrousersDescription = "Best one";
        String newBootsName = "Reebok";
        BigDecimal newPriceOfShirt = new BigDecimal("56.40");

        actualTrousers.orElseThrow(ProductNotFoundException::new).setDescription(newTrousersDescription);
        productRepository.save(actualTrousers.get());
        actualBoots.orElseThrow(ProductNotFoundException::new).setName(newBootsName);
        productRepository.save(actualBoots.get());
        actualShirt.orElseThrow(ProductNotFoundException::new).setPrice(newPriceOfShirt);
        productRepository.save(actualShirt.get());

        actualTrousers = productRepository.findById(trousersId);
        actualBoots = productRepository.findById(bootsId);
        actualShirt = productRepository.findById(shirtId);
        Optional<Group> actualClothes = groupRepository.findById(clothesId);

        assertTrue(actualClothes.isPresent());
        assertEquals(newTrousersDescription, actualTrousers.orElseThrow(ProductNotFoundException::new).getDescription());
        assertEquals(newBootsName, actualBoots.orElseThrow(ProductNotFoundException::new).getName());
        assertEquals(newPriceOfShirt, actualShirt.orElseThrow(ProductNotFoundException::new).getPrice());
        assertTrue(actualClothes.get().getProducts().stream()
                .anyMatch(e -> e.getName().equals(newBootsName)));
        assertTrue(actualClothes.get().getProducts().stream()
                .anyMatch(e -> e.getDescription().equals(newTrousersDescription)));
        assertTrue(actualClothes.get().getProducts().stream()
                .anyMatch(e -> e.getPrice().equals(newPriceOfShirt)));

        //CleanUp
        productDbService.deleteById(trousersId);
        productDbService.deleteById(bootsId);
        productDbService.deleteById(shirtId);
        groupDbService.deleteById(clothesId);
    }
}
*/
