package com.example.cafemanagement.repository;

import com.example.cafemanagement.model.Table;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableRepository extends JpaRepository<Table, Integer> {
}
