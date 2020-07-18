package com.example.cafemanagement.dto;

import com.example.cafemanagement.model.Role;
import com.example.cafemanagement.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Data Transfer Object for the users. */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
  private Integer id;
  private String firstName;
  private String lastName;
  private String username;
  private String password;
  private Role role;

  public UserDTO(User user) {
    this.id = user.getId();
    this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
    this.username = user.getUsername();
    this.password = null;
    this.role = user.getRole();
  }
}
