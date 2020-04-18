package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductDbService {
    @Autowired
    private ProductRepository productRepository;

    public void deleteById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            return;
        }
        product.get().setGroup(null);
        productRepository.save(product.get());
        productRepository.delete(product.get());
    }

    public List<Product> getProductsFromIdList(List<Long> idList) {
        List<Product> productList = new ArrayList<>();
        idList.forEach(id -> productList.add(productRepository.findById(id).get()));
        return productList;
    }

    public List<Long> getProductsIdList(List<Product> productList) {
        List<Long> productsIdList = new ArrayList<>();
        productList.forEach(p -> productsIdList.add(p.getId()));
        return productsIdList;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public Optional<Product> getProduct(Long id) {
        return productRepository.findById(id);
    }
}
