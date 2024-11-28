package com.nirmalks.bookstore.dto;

import java.util.List;

public class DirectOrderRequest {
    Long userId;
    OrderItemDto item;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public OrderItemDto getItem() {
        return item;
    }

    public void setItem(OrderItemDto item) {
        this.item = item;
    }
}
