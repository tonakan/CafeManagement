package com.example.cafemanagement.service;

import com.example.cafemanagement.dto.UserDTO;
import com.example.cafemanagement.model.User;
import com.example.cafemanagement.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/** Logic implementation for users. */
@AllArgsConstructor
@Service
public class UserService {
  private static final String[] IGNORE_FIELDS = {"id", "password"};
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  /**
   * Reads all the users from repository.
   *
   * @return the list of all users.
   */
  @NonNull
  public List<User> findAll() {
    return userRepository.findAll();
  }

  /**
   * Searches for the user by the given {@code id} in the repository.
   *
   * @param id the user's identifier.
   * @return {@link java.util.Optional} of the user.
   */
  @NonNull
  public Optional<User> findById(int id) {
    return userRepository.findById(id);
  }

  /**
   * Creates a user from the provided data.
   *
   * @param userDTO object describing user.
   * @return the created user.
   */
  @NonNull
  public User create(@NonNull UserDTO userDTO) {
    userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
    userDTO.setId(null);
    return userRepository.saveAndFlush(initUser(userDTO));
  }

  /**
   * Updates a user based on the provided data.
   *
   * @param userDTO object describing user.
   * @return the updated user.
   */
  @NonNull
  public Optional<User> update(@NonNull UserDTO userDTO) {
    Optional<User> user = userRepository.findById(userDTO.getId());
    if (!user.isPresent()) {
      return user;
    }
    BeanUtils.copyProperties(initUser(userDTO), user.get(), IGNORE_FIELDS);
    return Optional.of(userRepository.saveAndFlush(user.get()));
  }

  /**
   * Deletes a user with the given {@code id}.
   *
   * @param id the user's identifier.
   * @return {@link java.util.Optional} of the deleted user.
   */
  @NonNull
  public Optional<User> delete(int id) {
    Optional<User> user = userRepository.findById(id);
    if (user.isPresent()) {
      userRepository.deleteById(id);
    }
    return user;
  }

  /**
   * Search for a user with the given {@code username}.
   *
   * @param username the user's username.
   * @return if such user exists - the user, otherwise - null.
   */
  @Nullable
  public User findByUsername(@NonNull String username) {
    return userRepository.findByUsername(username);
  }

  @NonNull
  private User initUser(@NonNull UserDTO userDTO) {
    User user = new User();
    user.setId(userDTO.getId());
    user.setFirstName(userDTO.getFirstName());
    user.setLastName(userDTO.getLastName());
    user.setPassword(userDTO.getPassword());
    user.setUsername(userDTO.getUsername());
    user.setRole(userDTO.getRole());
    return user;
  }
}
