package com.example.cafemanagement.dto;

import com.example.cafemanagement.model.ProductInOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Data Transfer Object for the products. */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductInOrderDTO {
  private Integer id;
  private Integer amount;
  private ProductInOrderStatus status;
  private Integer productId;
  private Integer orderId;
}
