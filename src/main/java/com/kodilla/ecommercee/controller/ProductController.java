package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.ProductDto;
import com.kodilla.ecommercee.exceptions.ProductNotFoundException;
import com.kodilla.ecommercee.mapper.ProductMapper;
import com.kodilla.ecommercee.service.ProductDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/products")
public class ProductController {
    private ProductDbService productDbService;
    private ProductMapper productMapper;

    @Autowired
    public ProductController(ProductDbService productDbService, ProductMapper productMapper) {
        this.productDbService = productDbService;
        this.productMapper = productMapper;
    }

    @GetMapping
    public List<ProductDto> get() {
        return productMapper.mapToProductDtoList(productDbService.getProducts());
    }

    @GetMapping("/{id}")
    public ProductDto get(@PathVariable Long id) throws ProductNotFoundException {
        return productMapper.mapToProductDto(productDbService.getProduct(id).orElseThrow(ProductNotFoundException::new));

    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@RequestBody ProductDto productDto) {
        productDbService.saveProduct(productMapper.mapToProduct(productDto));
    }

    @PutMapping
    public void update(@RequestBody ProductDto productDto) {
        productDbService.saveProduct(productMapper.mapToProduct(productDto));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productDbService.deleteById(id);
    }
}
