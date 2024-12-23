package com.nirmalks.bookstore.order.api;

import com.nirmalks.bookstore.order.dto.OrderSummaryDto;
import com.nirmalks.bookstore.user.dto.UserDto;

public class OrderResponse {

    private String message;
    private OrderSummaryDto order;
    private UserDto user;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OrderSummaryDto getOrder() {
        return order;
    }

    public void setOrder(OrderSummaryDto order) {
        this.order = order;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "OrderResponseDto{" +
                "message='" + message + '\'' +
                ", order=" + order +
                ", user=" + user +
                '}';
    }
}
