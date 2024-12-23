package com.nirmalks.bookstore.order.api;

import com.nirmalks.bookstore.order.dto.OrderItemDto;
import jakarta.validation.constraints.NotNull;

public class DirectOrderRequest {
    @NotNull
    Long userId;
    @NotNull
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

    @Override
    public String toString() {
        return "DirectOrderRequest{" +
                "userId=" + userId +
                ", item=" + item +
                '}';
    }
}
