package com.nirmalks.bookstore.order.service;

import com.nirmalks.bookstore.order.api.DirectOrderRequest;
import com.nirmalks.bookstore.order.api.OrderFromCartRequest;
import com.nirmalks.bookstore.order.api.OrderResponse;
import com.nirmalks.bookstore.order.entity.Order;
import com.nirmalks.bookstore.order.entity.OrderStatus;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(DirectOrderRequest directOrderRequest);

    OrderResponse createOrder(OrderFromCartRequest orderFromCartRequest);

    List<Order> getOrdersByUser(Long userId);

    Order getOrder(Long orderId);

    void updateOrderStatus(Long orderId, OrderStatus status);
}
