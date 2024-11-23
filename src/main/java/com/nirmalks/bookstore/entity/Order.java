package com.nirmalks.bookstore.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "purchase_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private Long totalCost;

    @Enumerated
    private OrderStatus orderStatus;

    @Column(name = "placed_date", nullable = false, updatable = false)
    private LocalDateTime placedDate;

    @PrePersist
    public void setPlacedDate() {
        if (this.placedDate == null) {
            this.placedDate = LocalDateTime.now();
        }
    }
}
