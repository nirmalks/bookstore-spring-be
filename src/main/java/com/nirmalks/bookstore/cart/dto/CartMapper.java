package com.nirmalks.bookstore.cart.dto;

import com.nirmalks.bookstore.cart.api.CartItemRequest;
import com.nirmalks.bookstore.cart.api.CartResponse;
import com.nirmalks.bookstore.book.entity.Book;
import com.nirmalks.bookstore.cart.entity.Cart;
import com.nirmalks.bookstore.cart.entity.CartItem;

import java.util.Optional;

public class CartMapper {
    public static CartResponse toResponse(Cart cart) {
        CartResponse response = new CartResponse();
        response.setItems(cart.getCartItems().stream().map(
                CartMapper::toCartItemDto
        ).toList());
        response.setTotalPrice(cart.getTotalPrice());
        return response;
    }

    public static CartItemDto toCartItemDto(CartItem item) {
        return new CartItemDto(item.getId(), item.getBook().getId(), item.getQuantity(), item.getPrice());
    }

    public static Cart toEntity(Book book, Cart cart, CartItemRequest cartItemRequest) {
        Optional<CartItem> existingCartItem = cart.getCartItems().stream()
                .filter(item -> item.getBook().getId().equals(book.getId()))
                .findFirst();

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() +cartItemRequest.getQuantity());
            cartItem.setPrice(book.getPrice());
        } else {
            CartItem newCartItem = new CartItem();
            newCartItem.setBook(book);
            newCartItem.setQuantity(cartItemRequest.getQuantity());
            newCartItem.setPrice(book.getPrice());
            newCartItem.setCart(cart);
            cart.getCartItems().add(newCartItem);
        }
        cart.setTotalPrice(cart.calculateTotalPrice());
        return cart;
    }

    public static CartItem toCartItemEntity(Book book, CartItemDto itemDto) {
        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setPrice(itemDto.getPrice());
        cartItem.setQuantity(itemDto.getQuantity());
        return cartItem;
    }
}
