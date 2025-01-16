package com.nirmalks.bookstore.order.service.impl;

import com.nirmalks.bookstore.address.mapper.AddressMapper;
import com.nirmalks.bookstore.address.repository.AddressRepository;
import com.nirmalks.bookstore.book.entity.Book;
import com.nirmalks.bookstore.book.repository.BookRepository;
import com.nirmalks.bookstore.cart.entity.Cart;
import com.nirmalks.bookstore.cart.entity.CartItem;
import com.nirmalks.bookstore.cart.repository.CartRepository;
import com.nirmalks.bookstore.common.PageRequestDto;
import com.nirmalks.bookstore.common.RequestUtils;
import com.nirmalks.bookstore.exception.ResourceNotFoundException;
import com.nirmalks.bookstore.order.api.DirectOrderRequest;
import com.nirmalks.bookstore.order.api.OrderFromCartRequest;
import com.nirmalks.bookstore.order.api.OrderResponse;
import com.nirmalks.bookstore.order.dto.OrderMapper;
import com.nirmalks.bookstore.order.dto.OrderSummaryDto;
import com.nirmalks.bookstore.order.entity.Order;
import com.nirmalks.bookstore.order.entity.OrderItem;
import com.nirmalks.bookstore.order.entity.OrderStatus;
import com.nirmalks.bookstore.order.repository.OrderItemRepository;
import com.nirmalks.bookstore.order.repository.OrderRepository;
import com.nirmalks.bookstore.order.service.OrderService;
import com.nirmalks.bookstore.user.entity.User;
import com.nirmalks.bookstore.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    private final AddressRepository addressRepository;
    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository,
                        BookRepository bookRepository, OrderItemRepository orderItemRepository,
                            CartRepository cartRepository, AddressRepository addressRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartRepository = cartRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public OrderResponse createOrder(DirectOrderRequest directOrderRequest) {
        var userId = directOrderRequest.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        var itemDtos = directOrderRequest.getItems();
        var address = AddressMapper.toEntity(directOrderRequest.getAddress());
        address.setUser(user);
        addressRepository.save(address);
        var order = OrderMapper.toOrderEntity(user);
        order.setAddress(address);
        var orderItems = itemDtos.stream().map(itemDto -> {
            Book book = bookRepository.findById(itemDto.getBookId())
                    .orElseThrow(() -> new ResourceNotFoundException("Book not found for ID: " + itemDto.getBookId()));
            return OrderMapper.toOrderItemEntity(book, itemDto, order);
        }).toList();
        order.setItems(orderItems);
        order.setTotalCost(order.calculateTotalCost());
        var savedOrder = orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);
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

    public Page<OrderSummaryDto> getOrdersByUser(Long userId, PageRequestDto pageRequestDto) {
        var pageable = RequestUtils.getPageable(pageRequestDto);
        var orders = orderRepository.findAllByUserId(userId, pageable);
        return orders.map(OrderMapper::toOrderSummary);
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
