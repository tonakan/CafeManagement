package com.example.cafemanagement.service;

import com.example.cafemanagement.dto.OrderDTO;
import com.example.cafemanagement.model.Order;
import com.example.cafemanagement.model.OrderStatus;
import com.example.cafemanagement.model.Table;
import com.example.cafemanagement.model.User;
import com.example.cafemanagement.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/** Logic implementation for order. */
@AllArgsConstructor
@Service
public class OrderService {
  private static final String[] IGNORE_FIELDS = {"id", "creator", "created", "products"};
  private final OrderRepository orderRepository;
  private final TableService tableService;

  /**
   * Reads all the orders from repository.
   *
   * @return the list of all orders.
   */
  @NonNull
  public List<Order> findAll() {
    return orderRepository.findAll();
  }

  /**
   * Searches for the order by the given {@code id} in the repository.
   *
   * @param id the order's identifier.
   * @return {@link java.util.Optional} of the order.
   */
  @NonNull
  public Optional<Order> findById(int id) {
    return orderRepository.findById(id);
  }

  /**
   * Creates an order from the provided data.
   *
   * @param orderDTO object describing order.
   * @param creator the creator of the order.
   * @return the created order.
   */
  public Order create(OrderDTO orderDTO, User creator) {
    if (orderDTO.getTableId() == null) {
      return null;
    }
    List<Order> orders =
        orderRepository.findAllByTable_IdAndStatus(orderDTO.getTableId(), OrderStatus.OPEN);
    if (!orders.isEmpty()) {
      return null;
    }
    orderDTO.setId(null);
    orderDTO.setStatus(OrderStatus.OPEN);
    Order order = initOrder(orderDTO, creator);
    if (order == null) {
      return null;
    }
    return orderRepository.saveAndFlush(order);
  }

  /**
   * Updates order based on the provided data.
   *
   * @param orderDTO object describing order.
   * @return the updated order.
   */
  @NonNull
  public Optional<Order> update(OrderDTO orderDTO) {
    Optional<Order> order = Optional.empty();
    if (orderDTO.getTableId() == null) {
      return order;
    }
    order = orderRepository.findById(orderDTO.getId());
    if (!order.isPresent()) {
      return order;
    }
    List<Order> orders =
        orderRepository.findAllByTable_IdAndStatus(orderDTO.getTableId(), OrderStatus.OPEN);
    if (!orders.isEmpty()) {
      return order;
    }
    Order orderToUpdate = initOrder(orderDTO, null);
    if (orderToUpdate == null) {
      return order;
    }
    BeanUtils.copyProperties(orderToUpdate, order.get(), IGNORE_FIELDS);
    return Optional.of(orderRepository.saveAndFlush(order.get()));
  }

  @Nullable
  private Order initOrder(@NonNull OrderDTO orderDTO, @Nullable User user) {
    Optional<Table> table = tableService.findById(orderDTO.getTableId());
    if (!table.isPresent()) {
      return null;
    }
    Order order = new Order();
    order.setId(orderDTO.getId());
    order.setStatus(orderDTO.getStatus());
    order.setTable(table.get());
    order.setCreator(user);
    return order;
  }

  /**
   * Deletes a table with the given {@code id}.
   *
   * @param id the table's identifier.
   * @return {@link java.util.Optional} of the deleted table.
   */
  @NonNull
  public Optional<Order> delete(int id) {
    Optional<Order> order = orderRepository.findById(id);
    if (order.isPresent()) {
      orderRepository.deleteById(id);
    }
    return order;
  }
}
