package com.nirmalks.bookstore.cart.service;

import com.nirmalks.bookstore.cart.api.CartItemRequest;
import com.nirmalks.bookstore.cart.api.CartResponse;
import com.nirmalks.bookstore.book.entity.Book;
import com.nirmalks.bookstore.cart.entity.Cart;
import com.nirmalks.bookstore.cart.entity.CartItem;
import com.nirmalks.bookstore.user.entity.User;
import com.nirmalks.bookstore.exception.ResourceNotFoundException;
import com.nirmalks.bookstore.cart.dto.CartMapper;
import com.nirmalks.bookstore.book.repository.BookRepository;
import com.nirmalks.bookstore.cart.repository.CartItemRepository;
import com.nirmalks.bookstore.cart.repository.CartRepository;
import com.nirmalks.bookstore.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    public CartResponse getCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user"));
        return CartMapper.toResponse(cart);
    }

    public CartResponse addToCart(Long userId, CartItemRequest cartItemRequest) {
        Book book = bookRepository.findById(cartItemRequest.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> createCartForUser(userId));

        Cart updatedCart = cartRepository.save(CartMapper.toEntity(book,cart,cartItemRequest));
        return CartMapper.toResponse(updatedCart);
    }

    public void clearCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user"));
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }

    private Cart createCartForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    public void removeItemFromCart(Long cartId, Long itemId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        List<CartItem> updatedItems = cart.getCartItems().stream()
                .filter(item -> !item.getId().equals(itemId))
                .toList();

        cart.setCartItems(updatedItems);
        cart.setTotalPrice(updatedItems.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum());

        cartRepository.save(cart);
    }
}
