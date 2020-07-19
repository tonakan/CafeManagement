package com.example.cafemanagement.dto;

import com.example.cafemanagement.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Data Transfer Object for the products. */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDTO {
  private Integer id;
  private OrderStatus status;
  private Integer tableId;
}
