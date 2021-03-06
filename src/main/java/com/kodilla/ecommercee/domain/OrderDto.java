package com.kodilla.ecommercee.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class OrderDto {
    private Long id;
    private LocalDate creationDate;
    private String status;
    private Long userId;
    private List<ProductDto> products;
}
