package com.example.cafemanagement.controller;

import com.example.cafemanagement.dto.ProductDTO;
import com.example.cafemanagement.model.Product;
import com.example.cafemanagement.model.User;
import com.example.cafemanagement.service.ProductService;
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

/** REST controller for the products. */
@Api("Products")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
  private final ProductService productService;

  @PreAuthorize("hasRole('ROLE_MANAGER')")
  @ApiOperation(
          value = "Get all products.",
          nickname = "getAllProducts",
          authorizations = {@Authorization(value = "basicAuth")})
  @GetMapping
  public ResponseEntity<List<Product>> getAllProducts() {
    return ResponseEntity.ok(productService.findAll());
  }

  @PreAuthorize("hasRole('ROLE_MANAGER')")
  @ApiOperation(
          value = "Get a product by id.",
          nickname = "getProductById",
          authorizations = {@Authorization(value = "basicAuth")})
  @GetMapping("{id}")
  public ResponseEntity<Product> get(@PathVariable final Integer id) {
    if (id == null) {
      return ResponseEntity.badRequest().build();
    }
    Optional<Product> product = productService.findById(id);
    if (!product.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(product.get());
  }

  @PreAuthorize("hasRole('ROLE_MANAGER')")
  @ApiOperation(
          value = "Create a product.",
          nickname = "createProduct",
          authorizations = {@Authorization(value = "basicAuth")})
  @PostMapping
  public ResponseEntity<Product> create(@RequestBody ProductDTO product) {
    User creator =
            ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                    .getUser();
    Product createdProduct = productService.create(product, creator);
    return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
  }

  @PreAuthorize("hasRole('ROLE_MANAGER')")
  @ApiOperation(
          value = "Update a product.",
          nickname = "updateProduct",
          authorizations = {@Authorization(value = "basicAuth")})
  @PutMapping("{id}")
  public ResponseEntity<Product> update(
          @RequestBody ProductDTO product, @PathVariable final Integer id) {
    if (id == null) {
      return ResponseEntity.badRequest().build();
    }
    product.setId(id);
    Optional<Product> updatedProduct = productService.update(product);
    if (!updatedProduct.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(updatedProduct.get());
  }

  @PreAuthorize("hasRole('ROLE_MANAGER')")
  @ApiOperation(
          value = "Delete a product.",
          nickname = "deleteProduct",
          authorizations = {@Authorization(value = "basicAuth")})
  @DeleteMapping("{id}")
  public ResponseEntity<Product> delete(@PathVariable final Integer id) {
    if (id == null) {
      return ResponseEntity.badRequest().build();
    }
    Optional<Product> product = productService.delete(id);
    if (!product.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(product.get());
  }
}
