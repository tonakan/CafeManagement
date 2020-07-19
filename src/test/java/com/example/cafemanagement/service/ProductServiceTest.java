package com.example.cafemanagement.service;

import com.example.cafemanagement.CafemanagementApplication;
import com.example.cafemanagement.dto.ProductDTO;
import com.example.cafemanagement.model.Product;
import com.example.cafemanagement.model.User;
import com.example.cafemanagement.repository.ProductRepository;
import com.example.cafemanagement.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for ProductService class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CafemanagementApplication.class)
class ProductServiceTest {
  private static boolean isSet = false;
  @Autowired private ProductRepository productRepository;
  @Autowired private ProductService productService;
  @Autowired private UserRepository userRepository;

  @BeforeEach
  public void setUp() {
    if (isSet) {
      return;
    }
    productRepository.saveAndFlush(getProduct("1"));
    productRepository.saveAndFlush(getProduct("2"));
    userRepository.saveAndFlush(getUser());
    isSet = true;
  }

  private Product getProduct(String suffix) {
    Product product = new Product();
    product.setName("prod" + suffix);
    return product;
  }

  private User getUser() {
    User user = new User();
    user.setId(1);
    user.setUsername("username");
    user.setPassword("password");
    return user;
  }

  @Test
  public void testFindAll() {
    List<Product> products = productService.findAll();
    assertEquals(2, products.size());
  }

  @Test
  public void testFindById() {
    Optional<Product> product = productService.findById(2);
    assertTrue(product.isPresent());
    assertEquals("prod2", product.get().getName());
  }

  @Test
  public void testFindByIdInvalidId() {
    Optional<Product> product = productService.findById(5);
    assertFalse(product.isPresent());
  }

  @Test
  public void testCreate() {
    ProductDTO productDTO = new ProductDTO();
    productDTO.setName("createProd");
    User creator = getUser();
    Product product = productService.create(productDTO, creator);
    assertEquals(productDTO.getName(), product.getName());
    assertEquals(creator.getId(), product.getCreator().getId());
    productRepository.deleteById(product.getId());
  }

  @Test
  public void testUpdate() {
    Product product = productRepository.saveAndFlush(getProduct("update"));
    ProductDTO productDTO = new ProductDTO();
    productDTO.setName("newprodupdated");
    productDTO.setId(product.getId());
    Optional<Product> updated = productService.update(productDTO);
    assertTrue(updated.isPresent());
    assertEquals(productDTO.getName(), updated.get().getName());
    assertEquals(product.getId(), updated.get().getId());
    productRepository.deleteById(product.getId());
  }

  @Test
  public void testDelete() {
    Product product = new Product();
    product.setName("prod");
    product = productRepository.saveAndFlush(product);
    Optional<Product> deleted = productService.delete(product.getId());
    assertTrue(deleted.isPresent());
    assertEquals("prod", deleted.get().getName());
  }

  @Test
  public void testDeleteInvalidId() {
    Optional<Product> deleted = productService.delete(100);
    assertFalse(deleted.isPresent());
  }
}
