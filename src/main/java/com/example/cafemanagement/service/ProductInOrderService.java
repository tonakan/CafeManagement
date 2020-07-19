package com.example.cafemanagement.service;

import com.example.cafemanagement.dto.ProductInOrderDTO;
import com.example.cafemanagement.model.*;
import com.example.cafemanagement.repository.ProductInOrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/** Logic implementation for products in orders. */
@AllArgsConstructor
@Service
public class ProductInOrderService {
  private final ProductInOrderRepository productInOrderRepository;
  private final ProductService productService;
  private final OrderService orderService;

  /**
   * Reads all the products in orders from repository.
   *
   * @return the list of all products in orders.
   */
  @NonNull
  public List<ProductInOrder> findAll() {
    return productInOrderRepository.findAll();
  }

  /**
   * Searches for the product in order by the given {@code id} in the repository.
   *
   * @param id the product in order's identifier.
   * @return {@link java.util.Optional} of the product in order.
   */
  @NonNull
  public Optional<ProductInOrder> findById(int id) {
    return productInOrderRepository.findById(id);
  }

  /**
   * Updates a product in order based on the provided data.
   *
   * @param productInOrderDTO object describing product in order.
   * @return the created product in order.
   */
  @NonNull
  public ProductInOrder create(ProductInOrderDTO productInOrderDTO, User creator) {
    productInOrderDTO.setId(null);
    productInOrderDTO.setStatus(ProductInOrderStatus.ACTIVE);
    ProductInOrder productInOrder = initProductInOrder(productInOrderDTO, creator);
    if (productInOrder == null) {
      return null;
    }
    return productInOrderRepository.saveAndFlush(productInOrder);
  }

  /**
   * Updates a product in order based on the provided data.
   *
   * @param productInOrderDTO object describing product in order.
   * @return the updated product in order.
   */
  @NonNull
  public Optional<ProductInOrder> update(ProductInOrderDTO productInOrderDTO) {
    Optional<ProductInOrder> productInOrder =
        productInOrderRepository.findById(productInOrderDTO.getId());
    if (!productInOrder.isPresent()
        || productInOrderDTO.getStatus() == null
        || productInOrderDTO.getAmount() == null
        || productInOrderDTO.getAmount() == 0) {
      return productInOrder;
    }
    productInOrder.get().setStatus(productInOrderDTO.getStatus());
    productInOrder.get().setAmount(productInOrderDTO.getAmount());
    return Optional.of(productInOrderRepository.saveAndFlush(productInOrder.get()));
  }

  @NonNull
  private ProductInOrder initProductInOrder(
      @NonNull ProductInOrderDTO productInOrderDTO, @Nullable User user) {
    if (productInOrderDTO.getAmount() == null
        || productInOrderDTO.getAmount() == 0
        || productInOrderDTO.getProductId() == null
        || productInOrderDTO.getOrderId() == null) {
      return null;
    }
    Optional<Product> product = productService.findById(productInOrderDTO.getProductId());
    if (!product.isPresent()) {
      return null;
    }
    Optional<Order> order = orderService.findById(productInOrderDTO.getOrderId());
    if (!order.isPresent()) {
      return null;
    }
    ProductInOrder productInOrder = new ProductInOrder();
    productInOrder.setId(productInOrderDTO.getId());
    productInOrder.setAmount(productInOrderDTO.getAmount());
    productInOrder.setProduct(product.get());
    productInOrder.setOrder(order.get());
    productInOrder.setStatus(productInOrderDTO.getStatus());
    productInOrder.setCreator(user);
    return productInOrder;
  }

  /**
   * Deletes a product in order with the given {@code id}.
   *
   * @param id the product in order's identifier.
   * @return {@link java.util.Optional} of the deleted product in order.
   */
  @NonNull
  public Optional<ProductInOrder> delete(int id) {
    Optional<ProductInOrder> productInOrder = productInOrderRepository.findById(id);
    if (productInOrder.isPresent()) {
      productInOrderRepository.deleteById(id);
    }
    return productInOrder;
  }
}
