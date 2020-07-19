package com.example.cafemanagement.repository;

import com.example.cafemanagement.model.ProductInOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductInOrderRepository extends JpaRepository<ProductInOrder, Integer> {}
