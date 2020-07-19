package com.example.cafemanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "products")
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(unique = true)
  String name;

  @Column(nullable = false)
  Double price;

  @ManyToOne User creator;

  @Temporal(TemporalType.TIMESTAMP)
  private Date created = new Date();
}
