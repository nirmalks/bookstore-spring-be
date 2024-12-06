package com.nirmalks.bookstore.dto;

import jakarta.validation.constraints.NotNull;

public class OrderFromCartRequest {
    @NotNull
    Long cartId;
    @NotNull
    Long userId;

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
