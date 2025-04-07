package com.example.store.product.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;
    private Integer price;
    private Integer stockQuantity;
    private String imageUrl;
    private String description;
    private LocalDateTime createdDt;
    private LocalDateTime updatedDt;

    @PrePersist
    public void prePersist() {
        this.createdDt = LocalDateTime.now();
        this.updatedDt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedDt = LocalDateTime.now();
    }
}
