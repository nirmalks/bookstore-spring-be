package com.nirmalks.bookstore.order.controller;

import com.nirmalks.bookstore.common.PageRequestDto;
import com.nirmalks.bookstore.order.api.DirectOrderRequest;
import com.nirmalks.bookstore.order.api.OrderFromCartRequest;
import com.nirmalks.bookstore.order.api.OrderResponse;
import com.nirmalks.bookstore.order.dto.OrderSummaryDto;
import com.nirmalks.bookstore.order.entity.OrderStatus;
import com.nirmalks.bookstore.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/direct")
    public ResponseEntity<OrderResponse> createDirectOrder(@RequestBody DirectOrderRequest orderRequest) {
        var order = orderService.createOrder(orderRequest);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/from-cart")
    public ResponseEntity<OrderResponse> createOrderFromCart(@RequestBody OrderFromCartRequest orderRequest) {
        var order = orderService.createOrder(orderRequest);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Page<OrderSummaryDto>> getOrdersByUser(@PathVariable Long userId, PageRequestDto pageRequestDto) {
        var orders = orderService.getOrdersByUser(userId, pageRequestDto);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Void> updateOrderStatus(@PathVariable Long orderId, @RequestParam String status) {
        orderService.updateOrderStatus(orderId, OrderStatus.valueOf(status));
        return ResponseEntity.ok().build();
    }
}