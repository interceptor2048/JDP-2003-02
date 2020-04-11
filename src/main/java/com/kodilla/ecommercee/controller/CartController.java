package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.ProductDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/carts")
public class CartController {

    @PostMapping
    public void create() {
    }

    @GetMapping
    public List<ProductDto> get() {
        List<ProductDto> products = new ArrayList<>();
        products.add(new ProductDto(1L, "kurtka zimowa", "Pellentesque tempus interdum quam ut rhoncus.", BigDecimal.valueOf(100), "1"));
        products.add(new ProductDto(2L, "płaszcz", " Vivamus a bibendum purus.", BigDecimal.valueOf(150), "1"));
        products.add(new ProductDto(8L, "krawat", "Nunc mi mi, laoreet ac mollis nec, pharetra sit amet tortor.", BigDecimal.valueOf(50), "2"));
        return products;
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductDto> addProducts(@RequestBody List<ProductDto> products) {
        products.add(new ProductDto(1L, "kurtka zimowa", "Pellentesque tempus interdum quam ut rhoncus.", BigDecimal.valueOf(100), "1"));
        products.add(new ProductDto(2L, "płaszcz", " Vivamus a bibendum purus.", BigDecimal.valueOf(150), "1"));
        products.add(new ProductDto(8L, "krawat", "Nunc mi mi, laoreet ac mollis nec, pharetra sit amet tortor.", BigDecimal.valueOf(50), "2"));
        products.add(new ProductDto(5L, "torebka", "Nunc mi mi, laoreet ac mollis nec, pharetra sit amet tortor.", BigDecimal.valueOf(40), "3"));
        return products;
    }

    @DeleteMapping(value = "/{id}")
    public List<ProductDto> deleteProduct(@PathVariable Long id) {
        List<ProductDto> products = new ArrayList<>();
        products.add(new ProductDto(1L, "kurtka zimowa", "Pellentesque tempus interdum quam ut rhoncus.", BigDecimal.valueOf(100), "1"));
        products.add(new ProductDto(2L, "płaszcz", " Vivamus a bibendum purus.", BigDecimal.valueOf(150), "1"));
        return products;
    }

    @PostMapping(value = "/createOrder")
    public List<ProductDto> createOrder() {
        List<ProductDto> products = new ArrayList<>();
        products.add(new ProductDto(1L, "kurtka zimowa", "Pellentesque tempus interdum quam ut rhoncus.", BigDecimal.valueOf(100), "1"));
        products.add(new ProductDto(8L, "krawat", "Nunc mi mi, laoreet ac mollis nec, pharetra sit amet tortor.", BigDecimal.valueOf(50), "2"));
        products.add(new ProductDto(8L, "buty", "Nunc mi mi, laoreet ac mollis nec, pharetra sit amet tortor.", BigDecimal.valueOf(120), "3"));
        return products;
    }
}
