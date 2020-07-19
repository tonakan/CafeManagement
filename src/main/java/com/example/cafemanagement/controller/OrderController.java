package com.example.cafemanagement.controller;

import com.example.cafemanagement.dto.OrderDTO;
import com.example.cafemanagement.model.Order;
import com.example.cafemanagement.model.User;
import com.example.cafemanagement.service.OrderService;
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

/** REST controller for the orders. */
@Api(value = "Orders", tags = "Orders")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
  private OrderService orderService;

  @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_WAITER')")
  @ApiOperation(
      value = "Get all orders.",
      nickname = "getAllOrders",
      authorizations = {@Authorization(value = "basicAuth")})
  @GetMapping
  public ResponseEntity<List<Order>> getAllOrders() {
    return ResponseEntity.ok(orderService.findAll());
  }

  @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_WAITER')")
  @ApiOperation(
      value = "Get an order by id.",
      nickname = "getOrderById",
      authorizations = {@Authorization(value = "basicAuth")})
  @GetMapping("{id}")
  public ResponseEntity<Order> get(@PathVariable final Integer id) {
    if (id == null) {
      return ResponseEntity.badRequest().build();
    }
    Optional<Order> order = orderService.findById(id);
    if (!order.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(order.get());
  }

  @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_WAITER')")
  @ApiOperation(
      value = "Create an order.",
      nickname = "createOrder",
      authorizations = {@Authorization(value = "basicAuth")})
  @PostMapping
  public ResponseEntity<Order> create(@RequestBody OrderDTO order) {
    User creator =
        ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
            .getUser();
    Order createdOrder = orderService.create(order, creator);
    if (createdOrder == null) {
      return ResponseEntity.badRequest().build();
    }
    return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
  }

  @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_WAITER')")
  @ApiOperation(
      value = "Update an order.",
      nickname = "updateOrder",
      authorizations = {@Authorization(value = "basicAuth")})
  @PutMapping("{id}")
  public ResponseEntity<Order> update(@RequestBody OrderDTO order, @PathVariable final Integer id) {
    if (id == null) {
      return ResponseEntity.badRequest().build();
    }
    order.setId(id);
    Optional<Order> updatedOrder = orderService.update(order);
    if (!updatedOrder.isPresent()) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok(updatedOrder.get());
  }

  @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_WAITER')")
  @ApiOperation(
      value = "Delete a order.",
      nickname = "deleteOrder",
      authorizations = {@Authorization(value = "basicAuth")})
  @DeleteMapping("{id}")
  public ResponseEntity<Order> delete(@PathVariable final Integer id) {
    if (id == null) {
      return ResponseEntity.badRequest().build();
    }
    Optional<Order> order = orderService.delete(id);
    if (!order.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(order.get());
  }
}
