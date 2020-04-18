package com.kodilla.ecommercee.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class CartDto {
    private Long id;
    private BigDecimal totalPrice;
    private boolean isClosed;
    private Long userId;
    private List<ProductDto> cartItems;
}
