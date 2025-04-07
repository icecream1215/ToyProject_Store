package com.example.store.order.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Integer totalAmount;

    private LocalDateTime createdDt;
    private LocalDateTime updatedDt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    // DB에 insert되기 전에 호출
    @PrePersist
    public void prePersist() {
        this.createdDt = LocalDateTime.now();
        this.updatedDt = LocalDateTime.now();
    }

    // DB에 update되기 전에 호출
    @PreUpdate
    public void preUpdate() {
        this.updatedDt = LocalDateTime.now();
    }
}
