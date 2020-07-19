package com.example.cafemanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "products_in_order")
public class ProductInOrder {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  private Integer amount;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ProductInOrderStatus status;

  @ManyToOne
  private Product product;

  @ManyToOne
  private Order order;
}
