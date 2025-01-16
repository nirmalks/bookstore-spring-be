package com.nirmalks.bookstore.order.repository;

import com.nirmalks.bookstore.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM purchase_order o WHERE o.user.id = :userId")
    Page<Order> findAllByUserId(@Param("userId") Long userId, Pageable pageable);
}
