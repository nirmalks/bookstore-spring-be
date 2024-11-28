package com.nirmalks.bookstore.dto;

public class OrderResponseDto {

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
}
