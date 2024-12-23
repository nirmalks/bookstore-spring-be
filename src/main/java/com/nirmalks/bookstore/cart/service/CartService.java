package com.nirmalks.bookstore.cart.service;

import com.nirmalks.bookstore.cart.api.CartItemRequest;
import com.nirmalks.bookstore.cart.api.CartResponse;

public interface CartService {
    CartResponse getCart(Long userId);

    CartResponse addToCart(Long userId, CartItemRequest cartItemRequest);

    void clearCart(Long userId);

    void removeItemFromCart(Long cartId, Long itemId);
}
