package com.nirmalks.bookstore.order.dto;

import com.nirmalks.bookstore.book.entity.Book;
import com.nirmalks.bookstore.cart.entity.CartItem;
import com.nirmalks.bookstore.order.api.OrderItemRequest;
import com.nirmalks.bookstore.order.api.OrderResponse;
import com.nirmalks.bookstore.order.entity.Order;
import com.nirmalks.bookstore.order.entity.OrderItem;
import com.nirmalks.bookstore.order.entity.OrderStatus;
import com.nirmalks.bookstore.user.dto.UserMapper;
import com.nirmalks.bookstore.user.entity.User;

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

    public static OrderItem toOrderItemEntity(CartItem cartItem) {
        var orderItem = new OrderItem();
        orderItem.setBook(cartItem.getBook());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setPrice(cartItem.getPrice());
        return orderItem;
    }

    public static OrderResponse toResponse(User user, Order order, String message) {
        OrderResponse response = new OrderResponse();
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
        orderSummary.setItems(order.getItems().stream().map(OrderMapper::toOrderItemDto).toList());
        orderSummary.setAddress(order.getAddress());
        return orderSummary;
    }

    public static OrderItem toOrderItemEntity(Book book, OrderItemRequest itemDto, Order order) {
        var orderItem = new OrderItem();
        orderItem.setBook(book);
        orderItem.setQuantity(itemDto.getQuantity());
        orderItem.setPrice(itemDto.getPrice());
        orderItem.setOrder(order);
        return orderItem;
    }

    public static OrderItemDto toOrderItemDto(OrderItem orderItem) {
        var orderItemDto = new OrderItemDto();
        orderItemDto.setOrderId(orderItem.getOrder().getId());
        orderItemDto.setBookId(orderItem.getBook().getId());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setId(orderItem.getId());
        orderItemDto.setPrice(orderItem.getPrice());
        orderItemDto.setName(orderItem.getBook().getTitle());
        return orderItemDto;
    }
}
