package com.nirmalks.bookstore.cart.api;

import jakarta.validation.constraints.NotNull;

public class CartItemRequest {
    @NotNull
    private Long bookId;
    @NotNull
    private int quantity;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartItemRequest{" +
                "bookId=" + bookId +
                ", quantity=" + quantity +
                '}';
    }
}
