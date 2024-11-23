package com.nirmalks.bookstore.entity;

public enum OrderStatus {
    PENDING("Pending"),
    SHIPPED("Shipped"),
    CANCELLED("Cancelled");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
