package com.example.cafemanagement.repository;

import com.example.cafemanagement.model.Table;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TableRepository extends JpaRepository<Table, Integer> {
  List<Table> findByAssignedTo_Id(Integer id);
}
