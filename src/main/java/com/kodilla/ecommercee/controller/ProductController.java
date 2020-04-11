package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.ProductDto;
import com.kodilla.ecommercee.exceptions.ProductNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/products")
public class ProductController {

    @GetMapping
    public List<ProductDto> get() {
        return getMockProductList();
    }

    @GetMapping("/{id}")
    public ProductDto get(@PathVariable Long id) throws ProductNotFoundException {
        return ProductDto.builder()
                .id(1L)
                .name("Kurtka zimowa")
                .description("Pellentesque tempus interdum quam ut rhoncus. Donec ullamcorper turpis dolor")
                .price(new BigDecimal(100))
                .groupId("1")
                .build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@RequestBody ProductDto productDto) {
    }

    @PutMapping
    public ProductDto update(@RequestBody ProductDto productDto) {
        return ProductDto.builder()
                .id(1L)
                .name("Kurtka zimowa")
                .description("Pellentesque tempus interdum quam ut rhoncus. Donec ullamcorper turpis dolor")
                .price(new BigDecimal(100))
                .groupId("1")
                .build();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
    }

    private List<ProductDto> getMockProductList() {
        ArrayList<ProductDto> productsList = new ArrayList<>();
        ProductDto productDto1 = ProductDto.builder()
                .id(1L)
                .name("Kurtka zimowa")
                .description("Pellentesque tempus interdum quam ut rhoncus. Donec ullamcorper turpis dolor")
                .price(new BigDecimal(100))
                .groupId("1")
                .build();

        ProductDto productDto2 = ProductDto.builder()
                .id(2L)
                .name("płaszcz")
                .description("Pellentesque tempus interdum quam ut rhoncus. Donec ullamcorper turpis dolor")
                .price(new BigDecimal(150))
                .groupId("1")
                .build();

        ProductDto productDto3 = ProductDto.builder()
                .id(3L)
                .name("buty")
                .description("Pellentesque tempus interdum quam ut rhoncus. Donec ullamcorper turpis dolor")
                .price(new BigDecimal(120))
                .groupId("4")
                .build();

        productsList.add(productDto1);
        productsList.add(productDto2);
        productsList.add(productDto3);

        return productsList;
    }
}
