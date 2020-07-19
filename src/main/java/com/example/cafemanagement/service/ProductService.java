package com.example.cafemanagement.service;

import com.example.cafemanagement.dto.ProductDTO;
import com.example.cafemanagement.model.Product;
import com.example.cafemanagement.model.User;
import com.example.cafemanagement.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/** Logic implementation for product. */
@AllArgsConstructor
@Service
public class ProductService {
  private static final String[] IGNORE_FIELDS = {"id", "creator", "created"};
  private final ProductRepository productRepository;

  /**
   * Reads all the products from repository.
   *
   * @return the list of all products.
   */
  @NonNull
  public List<Product> findAll() {
    return productRepository.findAll();
  }

  /**
   * Searches for the product by the given {@code id} in the repository.
   *
   * @param id the product's identifier.
   * @return {@link java.util.Optional} of the product.
   */
  @NonNull
  public Optional<Product> findById(int id) {
    return productRepository.findById(id);
  }

  /**
   * Creates a product from the provided data.
   *
   * @param productDTO object describing product.
   * @param creator the creator of the product.
   * @return the created product.
   */
  public Product create(@NonNull ProductDTO productDTO, @NonNull User creator) {
    // TODO maybe add check for the products with the same name
    productDTO.setId(null);
    return productRepository.saveAndFlush(initProduct(productDTO, creator));
  }

  /**
   * Updates a product based on the provided data.
   *
   * @param productDTO object describing product.
   * @return the updated product.
   */
  @NonNull
  public Optional<Product> update(@NonNull ProductDTO productDTO) {
    Optional<Product> product = productRepository.findById(productDTO.getId());
    if (!product.isPresent()) {
      return product;
    }
    BeanUtils.copyProperties(initProduct(productDTO, null), product.get(), IGNORE_FIELDS);
    return Optional.of(productRepository.saveAndFlush(product.get()));
  }

  /**
   * Deletes a product with the given {@code id}.
   *
   * @param id the product's identifier.
   * @return {@link java.util.Optional} of the deleted product.
   */
  @NonNull
  public Optional<Product> delete(int id) {
    Optional<Product> product = productRepository.findById(id);
    if (product.isPresent()) {
      productRepository.deleteById(id);
    }
    return product;
  }

  @NonNull
  private Product initProduct(@NonNull ProductDTO productDTO, @Nullable User user) {
    Product product = new Product();
    product.setId(productDTO.getId());
    product.setName(productDTO.getName());
    product.setCreator(user);
    product.setPrice(productDTO.getPrice());
    return product;
  }
}
