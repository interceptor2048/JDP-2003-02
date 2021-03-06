package com.kodilla.ecommercee.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDto {
    private Long id;
    private BigDecimal totalPrice;
    private boolean isClosed;
    private Long userId;
    private List<ProductDto> cartItems;
}
