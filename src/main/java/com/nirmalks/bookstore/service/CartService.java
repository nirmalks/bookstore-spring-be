package com.nirmalks.bookstore.service;

import com.nirmalks.bookstore.dto.CartItemRequest;
import com.nirmalks.bookstore.dto.CartResponse;

public interface CartService {
    CartResponse getCart(Long userId);

    CartResponse addToCart(Long userId, CartItemRequest cartItemRequest);

    void clearCart(Long userId);

    void removeItemFromCart(Long cartId, Long itemId);
}
