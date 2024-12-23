package com.nirmalks.bookstore.cart.controller;

import com.nirmalks.bookstore.cart.api.CartItemRequest;
import com.nirmalks.bookstore.cart.api.CartResponse;
import com.nirmalks.bookstore.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public CartResponse getCart(@PathVariable Long userId) {
        return cartService.getCart(userId);
    }

    @PostMapping("/{userId}")
    public CartResponse addToCart(@PathVariable Long userId, @RequestBody CartItemRequest cartItemRequest) {
        return cartService.addToCart(userId, cartItemRequest);
    }

    @DeleteMapping("/{userId}")
    public void clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
    }

    @DeleteMapping("/{cartId}/item/{itemId}")
    public ResponseEntity<String> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId) {
        cartService.removeItemFromCart(cartId, itemId);
        return ResponseEntity.ok("Item removed from cart");
    }
}
