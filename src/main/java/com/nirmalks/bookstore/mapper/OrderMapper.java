package com.nirmalks.bookstore.mapper;

import com.nirmalks.bookstore.dto.OrderItemDto;
import com.nirmalks.bookstore.dto.OrderResponseDto;
import com.nirmalks.bookstore.dto.OrderSummaryDto;
import com.nirmalks.bookstore.entity.*;

import java.time.LocalDateTime;

public class OrderMapper {
    public static Order toOrderEntity(User user) {
        Order order = new Order();
        order.setUser(user);
        order.setTotalCost(order.calculateTotalCost());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setPlacedDate(LocalDateTime.now());
        return order;
    }

    public static OrderItem toOrderItemEntity(Book book, OrderItemDto itemDto) {
        var orderItem = new OrderItem();
        orderItem.setBook(book);
        orderItem.setQuantity(itemDto.getQuantity());
        orderItem.setPrice(orderItem.calculateItemCost());
        return orderItem;
    }

    public static OrderItem toOrderItemEntity(CartItem cartItem) {
        var orderItem = new OrderItem();
        orderItem.setBook(cartItem.getBook());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setPrice(cartItem.getPrice());
        return orderItem;
    }

    public static OrderResponseDto toResponse(User user, Order order, String message) {
        OrderResponseDto response = new OrderResponseDto();
        response.setMessage(message);
        response.setOrder(toOrderSummary(order));
        response.setUser(UserMapper.toDto(user));
        return response;
    }

    public static OrderSummaryDto toOrderSummary(Order order) {
        OrderSummaryDto orderSummary = new OrderSummaryDto();
        orderSummary.setId(order.getId());
        orderSummary.setStatus(order.getOrderStatus().toString());
        orderSummary.setPlacedDate(order.getPlacedDate());
        orderSummary.setTotalCost(order.getTotalCost());
        return orderSummary;
    }
}
