package com.example.cafemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Data Transfer Object for the products. */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TableDTO {
  private Integer id;
  private Integer assignedTo;
}
