package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
