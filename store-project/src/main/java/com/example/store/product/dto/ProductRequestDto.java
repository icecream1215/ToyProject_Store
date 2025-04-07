package com.example.store.product.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ProductRequestDto {
    private String itemName;
    private Integer price;
    private Integer stockQuantity;
    private String description;
    private MultipartFile image;
}
