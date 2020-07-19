package com.example.cafemanagement.service;

import com.example.cafemanagement.CafemanagementApplication;
import com.example.cafemanagement.dto.UserDTO;
import com.example.cafemanagement.model.Role;
import com.example.cafemanagement.model.User;
import com.example.cafemanagement.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/** Tests for UserService class. */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CafemanagementApplication.class)
class UserServiceTest {
  private static boolean isSet = false;

  @Autowired private UserRepository userRepository;
  @Autowired private UserService userService;
  @Autowired private PasswordEncoder passwordEncoder;

  @BeforeEach
  public void setUp() {
    if (isSet) {
      return;
    }
    userRepository.saveAndFlush(
        new User(null, "first1", "last1", "username1", "password1", Role.MANAGER));
    userRepository.saveAndFlush(
        new User(null, "first2", "last2", "username2", "password2", Role.WAITER));
    isSet = true;
  }

  @Test
  public void testFindAll() {
    List<User> users = userService.findAll();
    assertEquals(2, users.size());
  }

  @Test
  public void testFindById() {
    Optional<User> user = userService.findById(2);
    assertTrue(user.isPresent());
    assertEquals("username2", user.get().getUsername());
  }

  @Test
  public void testFindByIdInvalidId() {
    Optional<User> user = userService.findById(5);
    assertFalse(user.isPresent());
  }

  @Test
  public void testCreate() {
    UserDTO userDTO = new UserDTO();
    userDTO.setUsername("usernameCreate");
    userDTO.setPassword("passwordCreate");
    User user = userService.create(userDTO);
    assertNotNull(user);
    assertEquals(userDTO.getUsername(), user.getUsername());
    userRepository.deleteById(user.getId());
  }

  @Test
  public void testUpdateRole() {
    User user =
        userRepository.saveAndFlush(
            new User(null, null, null, "username", "password", Role.MANAGER));
    UserDTO userDTO = new UserDTO(user);
    userDTO.setRole(Role.WAITER);
    Optional<User> updated = userService.update(userDTO);
    assertTrue(updated.isPresent());
    assertEquals(user.getUsername(), updated.get().getUsername());
    assertEquals(user.getId(), updated.get().getId());
    assertEquals(Role.WAITER, updated.get().getRole());
    assertNull(updated.get().getFirstName());
    assertNull(updated.get().getLastName());
    userRepository.deleteById(user.getId());
  }

  @Test
  public void testUpdatePassword() {
    String newPassword = "newPassword";
    User user =
        userRepository.saveAndFlush(
            new User(null, null, null, "username", "password", Role.MANAGER));
    UserDTO userDTO = new UserDTO(user);
    userDTO.setPassword(newPassword);
    Optional<User> updated = userService.update(userDTO);
    assertTrue(updated.isPresent());
    assertEquals(user.getUsername(), updated.get().getUsername());
    assertEquals(user.getId(), updated.get().getId());
    assertNull(updated.get().getFirstName());
    assertNull(updated.get().getLastName());
    assertEquals(user.getPassword(), updated.get().getPassword());
    assertNotEquals(newPassword, updated.get().getPassword());
    userRepository.deleteById(user.getId());
  }

  @Test
  public void testUpdateInvalidId() {
    UserDTO userDTO = new UserDTO(new User(6, null, null, "username", "password", Role.MANAGER));
    Optional<User> updated = userService.update(userDTO);
    assertFalse(updated.isPresent());
  }

  @Test
  public void testDelete() {
    User user =
        userRepository.saveAndFlush(
            new User(null, null, null, "username", "password", Role.MANAGER));
    Optional<User> deleted = userService.delete(user.getId());
    assertTrue(deleted.isPresent());
    assertEquals("username", deleted.get().getUsername());
  }

  @Test
  public void testDeleteInvalidId() {
    Optional<User> deleted = userService.delete(10);
    assertFalse(deleted.isPresent());
  }

  @Test
  public void testFindByUserName() {
    User user1 = userService.findByUsername("username1");
    assertNotNull(user1);
    assertEquals("username1", user1.getUsername());
    User someUser = userService.findByUsername("someusername");
    assertNull(someUser);
  }
}
