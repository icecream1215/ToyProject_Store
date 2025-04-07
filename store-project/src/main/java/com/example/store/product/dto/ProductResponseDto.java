package com.example.store.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductResponseDto {
    private Long id;
    private String itemName;
    private Integer price;
    private Integer stockQuantity;
    private String imageUrl;
    private String description;
}