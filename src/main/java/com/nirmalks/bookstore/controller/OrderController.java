package com.nirmalks.bookstore.controller;

import com.nirmalks.bookstore.dto.DirectOrderRequest;
import com.nirmalks.bookstore.dto.OrderFromCartRequest;
import com.nirmalks.bookstore.dto.OrderResponseDto;
import com.nirmalks.bookstore.entity.Order;
import com.nirmalks.bookstore.entity.OrderStatus;
import com.nirmalks.bookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/direct")
    public ResponseEntity<OrderResponseDto> createDirectOrder(@RequestBody DirectOrderRequest orderRequest) {
        var order = orderService.createOrder(orderRequest);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/from-cart")
    public ResponseEntity<OrderResponseDto> createOrderFromCart(@RequestBody OrderFromCartRequest orderRequest) {
        var order = orderService.createOrder(orderRequest);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable Long userId) {
        var orders = orderService.getOrdersByUser(userId);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Void> updateOrderStatus(@PathVariable Long orderId, @RequestParam String status) {
        orderService.updateOrderStatus(orderId, OrderStatus.valueOf(status));
        return ResponseEntity.ok().build();
    }
}