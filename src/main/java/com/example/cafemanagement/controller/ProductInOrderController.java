package com.example.cafemanagement.controller;

import com.example.cafemanagement.dto.ProductInOrderDTO;
import com.example.cafemanagement.model.ProductInOrder;
import com.example.cafemanagement.model.User;
import com.example.cafemanagement.service.ProductInOrderService;
import com.example.cafemanagement.service.UserDetailsImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/** REST controller for the products in order. */
@Api(value = "Products In Orders", tags = "Products In Order")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/productInOrder")
public class ProductInOrderController {
  private final ProductInOrderService productInOrderService;

  @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_WAITER')")
  @ApiOperation(
      value = "Get all products in orders.",
      nickname = "getAllProductsInOrders",
      authorizations = {@Authorization(value = "basicAuth")})
  @GetMapping
  public ResponseEntity<List<ProductInOrder>> getAllProductsInOrders() {
    return ResponseEntity.ok(productInOrderService.findAll());
  }

  @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_WAITER')")
  @ApiOperation(
      value = "Get a product in order by id.",
      nickname = "getProductInOrderById",
      authorizations = {@Authorization(value = "basicAuth")})
  @GetMapping("{id}")
  public ResponseEntity<ProductInOrder> get(@PathVariable final Integer id) {
    if (id == null) {
      return ResponseEntity.badRequest().build();
    }
    Optional<ProductInOrder> productInOrder = productInOrderService.findById(id);
    if (!productInOrder.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(productInOrder.get());
  }

  @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_WAITER')")
  @ApiOperation(
      value = "Create a product in order.",
      nickname = "createProductInOrder",
      authorizations = {@Authorization(value = "basicAuth")})
  @PostMapping
  public ResponseEntity<ProductInOrder> create(@RequestBody ProductInOrderDTO productInOrder) {
    User creator =
        ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
            .getUser();
    ProductInOrder createdProductInOrder = productInOrderService.create(productInOrder, creator);
    if (productInOrder == null) {
      return ResponseEntity.badRequest().build();
    }
    return new ResponseEntity<>(createdProductInOrder, HttpStatus.CREATED);
  }

  @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_WAITER')")
  @ApiOperation(
      value = "Update a product in order.",
      nickname = "updateProductInOrder",
      authorizations = {@Authorization(value = "basicAuth")})
  @PutMapping("{id}")
  public ResponseEntity<ProductInOrder> update(
      @RequestBody ProductInOrderDTO productInOrder, @PathVariable final Integer id) {
    if (id == null) {
      return ResponseEntity.badRequest().build();
    }
    productInOrder.setId(id);
    Optional<ProductInOrder> updatedProduct = productInOrderService.update(productInOrder);
    if (!updatedProduct.isPresent()) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok(updatedProduct.get());
  }

  @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_WAITER')")
  @ApiOperation(
      value = "Delete a product in order.",
      nickname = "deleteProductInOrder",
      authorizations = {@Authorization(value = "basicAuth")})
  @DeleteMapping("{id}")
  public ResponseEntity<ProductInOrder> delete(@PathVariable final Integer id) {
    if (id == null) {
      return ResponseEntity.badRequest().build();
    }
    Optional<ProductInOrder> productInOrder = productInOrderService.delete(id);
    if (!productInOrder.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(productInOrder.get());
  }
}
