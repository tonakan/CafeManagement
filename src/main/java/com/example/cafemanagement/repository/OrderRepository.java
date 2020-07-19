package com.example.cafemanagement.repository;

import com.example.cafemanagement.model.Order;
import com.example.cafemanagement.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
  List<Order> findAllByTable_IdAndStatus(Integer tableId, OrderStatus status);
}
