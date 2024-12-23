package com.nirmalks.bookstore.order.service.impl;

import com.nirmalks.bookstore.book.entity.Book;
import com.nirmalks.bookstore.book.repository.BookRepository;
import com.nirmalks.bookstore.cart.entity.Cart;
import com.nirmalks.bookstore.cart.entity.CartItem;
import com.nirmalks.bookstore.cart.repository.CartRepository;
import com.nirmalks.bookstore.order.api.DirectOrderRequest;
import com.nirmalks.bookstore.order.api.OrderFromCartRequest;
import com.nirmalks.bookstore.order.api.OrderResponse;
import com.nirmalks.bookstore.exception.ResourceNotFoundException;
import com.nirmalks.bookstore.order.dto.OrderMapper;
import com.nirmalks.bookstore.order.entity.Order;
import com.nirmalks.bookstore.order.entity.OrderItem;
import com.nirmalks.bookstore.order.entity.OrderStatus;
import com.nirmalks.bookstore.order.repository.OrderItemRepository;
import com.nirmalks.bookstore.order.repository.OrderRepository;
import com.nirmalks.bookstore.order.service.OrderService;
import com.nirmalks.bookstore.user.entity.User;
import com.nirmalks.bookstore.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    private UserRepository userRepository;

    private BookRepository bookRepository;

    private OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository,
                        BookRepository bookRepository, OrderItemRepository orderItemRepository,
                            CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public OrderResponse createOrder(DirectOrderRequest directOrderRequest) {
        var userId = directOrderRequest.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        var itemDto = directOrderRequest.getItem();
        Book book = bookRepository.findById(itemDto.getBookId())
                    .orElseThrow(() -> new ResourceNotFoundException("Book not found for ID: " + itemDto.getBookId()));

        var orderItem = OrderMapper.toOrderItemEntity(book, itemDto);
        var order = OrderMapper.toOrderEntity(user);
        order.setItems(List.of(orderItem));
        var savedOrder = orderRepository.save(order);
        orderItemRepository.save(orderItem);
        return OrderMapper.toResponse(user, savedOrder, "Order placed successfully.");
    }

    @Override
    public OrderResponse createOrder(OrderFromCartRequest orderFromCartRequest) {
        Cart cart = cartRepository.findById(orderFromCartRequest.getCartId()).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        User user = userRepository.findById(orderFromCartRequest.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<OrderItem> orderItems = new ArrayList<>();

        for(CartItem cartItem: cart.getCartItems()) {
            var orderItem = OrderMapper.toOrderItemEntity(cartItem);
            orderItems.add(orderItem);
        }

        Order order = new Order();
        order.setUser(user);
        order.setItems(orderItems);
        order.setTotalCost(cart.getTotalPrice());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setPlacedDate(LocalDateTime.now());
        Order savedOrder = orderRepository.save(order);

        for (OrderItem item : orderItems) {
            item.setOrder(savedOrder);
            orderItemRepository.save(item);
        }
        return OrderMapper.toResponse(user, savedOrder, "Order placed successfully.");
    }

    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findAllByUserId(userId);
    }

    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    public void updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.setOrderStatus(status);
        orderRepository.save(order);
    }
}
