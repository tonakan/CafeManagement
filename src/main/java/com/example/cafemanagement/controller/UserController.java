package com.example.cafemanagement.controller;

import com.example.cafemanagement.dto.UserDTO;
import com.example.cafemanagement.model.User;
import com.example.cafemanagement.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/** REST controller for the users. */
@Api("Users")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
  private final UserService userService;

  @PreAuthorize("hasRole('ROLE_MANAGER')")
  @ApiOperation(
      value = "Get all users.",
      nickname = "getAllUsers",
      authorizations = {@Authorization(value = "basicAuth")})
  @GetMapping
  public ResponseEntity<List<UserDTO>> getAllUsers() {
    List<UserDTO> users =
        userService.findAll().stream().map(UserDTO::new).collect(Collectors.toList());
    return ResponseEntity.ok(users);
  }

  @PreAuthorize("hasRole('ROLE_MANAGER')")
  @ApiOperation(
      value = "Get a user by id.",
      nickname = "getUserById",
      authorizations = {@Authorization(value = "basicAuth")})
  @GetMapping("{id}")
  public ResponseEntity<UserDTO> get(@PathVariable final Integer id) {
    if (id == null) {
      return ResponseEntity.badRequest().build();
    }
    Optional<User> user = userService.findById(id);
    if (!user.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(new UserDTO(user.get()));
  }

  @PreAuthorize("hasRole('ROLE_MANAGER')")
  @ApiOperation(
      value = "Create a user.",
      nickname = "createUser",
      authorizations = {@Authorization(value = "basicAuth")})
  @PostMapping
  public ResponseEntity<User> create(@RequestBody UserDTO user) {
    User createdUser = userService.create(user);
    return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
  }

  @PreAuthorize("hasRole('ROLE_MANAGER')")
  @ApiOperation(
      value = "Update a user.",
      nickname = "updateUser",
      authorizations = {@Authorization(value = "basicAuth")})
  @PutMapping("{id}")
  public ResponseEntity<UserDTO> update(@RequestBody UserDTO user, @PathVariable final Integer id) {
    if (id == null) {
      return ResponseEntity.badRequest().build();
    }
    user.setId(id);
    Optional<User> updatedUser = userService.update(user);
    if (!updatedUser.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(new UserDTO(updatedUser.get()));
  }

  @PreAuthorize("hasRole('ROLE_MANAGER')")
  @ApiOperation(
      value = "Delete a user.",
      nickname = "deleteUser",
      authorizations = {@Authorization(value = "basicAuth")})
  @DeleteMapping("{id}")
  public ResponseEntity<UserDTO> delete(@PathVariable final Integer id) {
    if (id == null) {
      return ResponseEntity.badRequest().build();
    }
    Optional<User> user = userService.delete(id);
    if (!user.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(new UserDTO(user.get()));
  }
}
