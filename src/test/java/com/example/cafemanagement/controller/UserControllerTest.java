package com.example.testingweb;

import com.example.cafemanagement.CafemanagementApplication;
import com.example.cafemanagement.controller.UserController;
import com.example.cafemanagement.dto.UserDTO;
import com.example.cafemanagement.model.Role;
import com.example.cafemanagement.model.User;
import com.example.cafemanagement.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** Tests for UserController class. */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CafemanagementApplication.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
  private static final String URI = "/api/v1/user/";
  @Autowired private MockMvc mvc;
  @Autowired ObjectMapper objectMapper;

  @MockBean private UserService service;

  @Test
  @WithMockUser(
      username = "admin",
      roles = {"MANAGER"})
  public void getAll() throws Exception {
    User user = new User();
    user.setId(1);
    user.setUsername("username");

    List<User> allUsers = Arrays.asList(user);

    given(service.findAll()).willReturn(allUsers);

    mvc.perform(get(URI).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
        .andExpect(
            MockMvcResultMatchers.jsonPath(
                "$[0].username", org.hamcrest.core.Is.is(user.getUsername())));
  }

  @Test
  @WithMockUser(
      username = "admin",
      roles = {"WAITER"})
  public void getAllForbidden() throws Exception {
    mvc.perform(get(URI).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(
      username = "admin",
      roles = {"MANAGER"})
  public void getByIdNotFound() throws Exception {
    given(service.findById(1)).willReturn(Optional.empty());

    mvc.perform(get(URI + "1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(
      username = "admin",
      roles = {"WAITER"})
  public void getByIdForbidden() throws Exception {
    mvc.perform(get(URI + "1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(
      username = "admin",
      roles = {"MANAGER"})
  public void getById() throws Exception {
    User user = new User();
    user.setId(1);
    user.setUsername("username");

    given(service.findById(1)).willReturn(Optional.of(user));

    mvc.perform(get(URI + "1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(
            MockMvcResultMatchers.jsonPath(
                "$.username", org.hamcrest.core.Is.is(user.getUsername())));
  }

  @Test
  @WithMockUser(
      username = "admin",
      roles = {"MANAGER"})
  public void testCreate() throws Exception {
    User user = new User();
    user.setUsername("username");
    user.setPassword("password");
    user.setRole(Role.WAITER);

    given(service.create(argThat(new UserDTOMatcher(new UserDTO())))).willReturn(user);

    mvc.perform(
            post(URI)
                .content(objectMapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.role", org.hamcrest.core.Is.is("WAITER")))
        .andExpect(
            MockMvcResultMatchers.jsonPath(
                "$.username", org.hamcrest.core.Is.is(user.getUsername())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.password").value(IsNull.nullValue()));
  }

  @Test
  @WithMockUser(
      username = "admin",
      roles = {"WAITER"})
  public void testCreateForbidden() throws Exception {
    User user = new User();
    user.setUsername("username");
    user.setPassword("password");
    user.setRole(Role.WAITER);

    mvc.perform(
            post(URI)
                .content(objectMapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(
      username = "admin",
      roles = {"MANAGER"})
  public void testUpdate() throws Exception {
    String id = "1";
    User user = new User();
    user.setUsername("username");
    user.setPassword("password");
    user.setRole(Role.WAITER);

    given(service.update(argThat(new UserDTOMatcher(new UserDTO())))).willReturn(Optional.of(user));

    mvc.perform(
            put(URI + id)
                .content(objectMapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.role", org.hamcrest.core.Is.is("WAITER")))
        .andExpect(
            MockMvcResultMatchers.jsonPath(
                "$.username", org.hamcrest.core.Is.is(user.getUsername())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.password").value(IsNull.nullValue()));
  }

  @Test
  @WithMockUser(
      username = "admin",
      roles = {"MANAGER"})
  public void testUpdateNotFound() throws Exception {
    String id = "1";
    User user = new User();
    user.setUsername("username");
    user.setPassword("password");
    user.setRole(Role.WAITER);

    given(service.update(argThat(new UserDTOMatcher(new UserDTO())))).willReturn(Optional.empty());

    mvc.perform(
            put(URI + id)
                .content(objectMapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(
      username = "admin",
      roles = {"WAITER"})
  public void testUpdateForbidden() throws Exception {
    String id = "1";
    User user = new User();
    user.setUsername("username");
    user.setPassword("password");
    user.setRole(Role.MANAGER);

    mvc.perform(
            put(URI + id)
                .content(objectMapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(
      username = "admin",
      roles = {"MANAGER"})
  public void testDelete() throws Exception {
    String id = "1";
    User user = new User();
    user.setUsername("username");
    user.setPassword("password");
    user.setRole(Role.MANAGER);

    given(service.delete(eq(1))).willReturn(Optional.of(user));

    mvc.perform(delete(URI + id).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.role", org.hamcrest.core.Is.is("MANAGER")))
        .andExpect(
            MockMvcResultMatchers.jsonPath(
                "$.username", org.hamcrest.core.Is.is(user.getUsername())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.password").value(IsNull.nullValue()));
  }

  @Test
  @WithMockUser(
      username = "admin",
      roles = {"MANAGER"})
  public void testDeleteNotFound() throws Exception {
    String id = "1";

    given(service.delete(eq(1))).willReturn(Optional.empty());

    mvc.perform(delete(URI + id).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(
      username = "admin",
      roles = {"WAITER"})
  public void testDeleteForbidden() throws Exception {
    String id = "1";

    mvc.perform(delete(URI + id).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @AllArgsConstructor
  static class UserDTOMatcher implements ArgumentMatcher<UserDTO> {
    public UserDTO userDTO;

    @Override
    public boolean matches(UserDTO userDTO) {
      return Objects.equals(userDTO.getUsername(), userDTO.getUsername())
          && Objects.equals(userDTO.getId(), userDTO.getId())
          && Objects.equals(userDTO.getPassword(), userDTO.getPassword())
          && Objects.equals(userDTO.getRole(), userDTO.getRole())
          && Objects.equals(userDTO.getFirstName(), userDTO.getFirstName())
          && Objects.equals(userDTO.getLastName(), userDTO.getLastName());
    }
  }
}
