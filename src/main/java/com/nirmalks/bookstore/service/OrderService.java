package com.nirmalks.bookstore.service;

import com.nirmalks.bookstore.dto.DirectOrderRequest;
import com.nirmalks.bookstore.dto.OrderFromCartRequest;
import com.nirmalks.bookstore.dto.OrderResponseDto;
import com.nirmalks.bookstore.entity.Order;
import com.nirmalks.bookstore.entity.OrderStatus;

import java.util.List;

public interface OrderService {
    OrderResponseDto createOrder(DirectOrderRequest directOrderRequest);

    OrderResponseDto createOrder(OrderFromCartRequest orderFromCartRequest);

    List<Order> getOrdersByUser(Long userId);

    Order getOrder(Long orderId);

    void updateOrderStatus(Long orderId, OrderStatus status);
}
