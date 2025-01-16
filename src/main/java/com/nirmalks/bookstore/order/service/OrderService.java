package com.nirmalks.bookstore.order.service;

import com.nirmalks.bookstore.common.PageRequestDto;
import com.nirmalks.bookstore.order.api.DirectOrderRequest;
import com.nirmalks.bookstore.order.api.OrderFromCartRequest;
import com.nirmalks.bookstore.order.api.OrderResponse;
import com.nirmalks.bookstore.order.dto.OrderSummaryDto;
import com.nirmalks.bookstore.order.entity.Order;
import com.nirmalks.bookstore.order.entity.OrderStatus;
import org.springframework.data.domain.Page;

public interface OrderService {
    OrderResponse createOrder(DirectOrderRequest directOrderRequest);

    OrderResponse createOrder(OrderFromCartRequest orderFromCartRequest);

    Page<OrderSummaryDto> getOrdersByUser(Long userId, PageRequestDto pageRequestDto);

    Order getOrder(Long orderId);

    void updateOrderStatus(Long orderId, OrderStatus status);
}
