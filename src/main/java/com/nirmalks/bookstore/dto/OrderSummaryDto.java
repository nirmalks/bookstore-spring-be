package com.nirmalks.bookstore.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderSummaryDto {
    private Long id;
    private String status;
    private LocalDateTime placedDate;
    private Double totalCost;
    private List<OrderItemDto> items;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getPlacedDate() {
        return placedDate;
    }

    public void setPlacedDate(LocalDateTime placedDate) {
        this.placedDate = placedDate;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "OrderSummaryDto{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", placedDate=" + placedDate +
                ", totalCost=" + totalCost +
                ", items=" + items +
                '}';
    }
}
