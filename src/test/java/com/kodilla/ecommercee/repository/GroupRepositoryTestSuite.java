/*
package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.Product;
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

@RunWith(SpringRunner.class)
@SpringBootTest
public class GroupRepositoryTestSuite {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private GroupDbService groupDbService;
    @Autowired
    private ProductDbService productDbService;
    @Autowired
    private ProductRepository productRepository;

    private Group getTestGroup() {
        String name = "dresses";
        List<Product> products = new ArrayList<>();
        Group dresses = new Group(null, name, products);
        Product product1 = new Product(null, "P1", "Pellentesque tempus interdum quam ut rhoncus.", BigDecimal.valueOf(230), dresses);
        Product product2 = new Product(null, "P2", "Tempus interdum quam ut rhoncus.", BigDecimal.valueOf(255), dresses);
        Product product3 = new Product(null, "P3", "Interdum quam ut rhoncus.", BigDecimal.valueOf(543), dresses);
        products.add(product1);
        products.add(product2);
        products.add(product3);

        return dresses;
    }

    private void cleanUp(Group group) {
        group.getProducts().stream().forEach(p -> productDbService.deleteById(p.getId()));
        groupDbService.deleteById(group.getId());

        Assert.assertEquals(0, groupRepository.count());
        Assert.assertEquals(0, productRepository.count());
    }

    @Test
    public void testSaveGroupWithProducts() {
        //Given
        Group group = getTestGroup();

        //When
        groupRepository.save(group);

        //Then
        Long groupId = group.getId();
        Optional<Group> actualGroup = groupRepository.findById(groupId);
        Assert.assertTrue(actualGroup.isPresent());
        Assert.assertEquals("dresses", actualGroup.get().getName());
        Assert.assertEquals(3, actualGroup.get().getProducts().size());

        //CleanUp
        cleanUp(actualGroup.get());
    }

    @Test
    public void testDeleteProductWithGroup() {
        //Given
        Group group = getTestGroup();

        //When
        groupRepository.save(group);
        Long productId = group.getProducts().get(0).getId();
        productDbService.deleteById(productId);

        //Then
        Long groupId = group.getId();
        Optional<Group> actualGroup = groupRepository.findById(groupId);
        Assert.assertTrue(actualGroup.isPresent());
        Assert.assertEquals(2, actualGroup.get().getProducts().size());

        //CleanUp
        cleanUp(actualGroup.get());
    }

    @Test
    public void testUpdateGroup() {
        //Given
        Group group = getTestGroup();
        String newGroupName = "Clothes";

        //When
        groupRepository.save(group);
        group.setName(newGroupName);
        groupRepository.save(group);

        //Then
        Long groupId = group.getId();
        Optional<Group> actualGroup = groupRepository.findById(groupId);
        Assert.assertTrue(actualGroup.isPresent());
        Assert.assertEquals(newGroupName, actualGroup.get().getName());

        //CleanUp
        cleanUp(actualGroup.get());
    }
}
*/
