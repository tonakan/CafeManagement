package com.example.cafemanagement.controller;

import com.example.cafemanagement.dto.TableDTO;
import com.example.cafemanagement.model.Table;
import com.example.cafemanagement.model.User;
import com.example.cafemanagement.service.TableService;
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

/** REST controller for the tables. */
@Api("Tables")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/table")
public class TableController {
  private final TableService tableService;

  @PreAuthorize("hasRole('ROLE_MANAGER')")
  @ApiOperation(
          value = "Get all tables.",
          nickname = "getAllTables",
          authorizations = {@Authorization(value = "basicAuth")})
  @GetMapping
  public ResponseEntity<List<Table>> getAllTables() {
    return ResponseEntity.ok(tableService.findAll());
  }

  @PreAuthorize("hasRole('ROLE_MANAGER')")
  @ApiOperation(
          value = "Get a table by id.",
          nickname = "getTableById",
          authorizations = {@Authorization(value = "basicAuth")})
  @GetMapping("{id}")
  public ResponseEntity<Table> get(@PathVariable final Integer id) {
    if (id == null) {
      return ResponseEntity.badRequest().build();
    }
    Optional<Table> table = tableService.findById(id);
    if (!table.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(table.get());
  }

  @PreAuthorize("hasRole('ROLE_MANAGER')")
  @ApiOperation(
          value = "Create a table.",
          nickname = "createTable",
          authorizations = {@Authorization(value = "basicAuth")})
  @PostMapping
  public ResponseEntity<Table> create(@RequestBody TableDTO table) {
    User creator =
            ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                    .getUser();
    Table createdTable = tableService.create(table, creator);
    if (createdTable == null) {
      return ResponseEntity.badRequest().build();
    }
    return new ResponseEntity<>(createdTable, HttpStatus.CREATED);
  }

  @PreAuthorize("hasRole('ROLE_MANAGER')")
  @ApiOperation(
          value = "Update a table.",
          nickname = "updateTable",
          authorizations = {@Authorization(value = "basicAuth")})
  @PutMapping("{id}")
  public ResponseEntity<Table> update(@RequestBody TableDTO table, @PathVariable final Integer id) {
    if (id == null) {
      return ResponseEntity.badRequest().build();
    }
    table.setId(id);
    Optional<Table> updatedTable = tableService.update(table);
    if (!updatedTable.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(updatedTable.get());
  }

  @PreAuthorize("hasRole('ROLE_MANAGER')")
  @ApiOperation(
          value = "Delete a table.",
          nickname = "deleteTable",
          authorizations = {@Authorization(value = "basicAuth")})
  @DeleteMapping("{id}")
  public ResponseEntity<Table> delete(@PathVariable final Integer id) {
    if (id == null) {
      return ResponseEntity.badRequest().build();
    }
    Optional<Table> table = tableService.delete(id);
    if (!table.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(table.get());
  }

  @PreAuthorize("hasRole('ROLE_WAITER')")
  @ApiOperation(
          value = "Get all tables assigned to waiter.",
          nickname = "getAllAssignedTables",
          authorizations = {@Authorization(value = "basicAuth")})
  @GetMapping("/assigned")
  public ResponseEntity<List<Table>> getAllAssignedTables() {
    User waiter =
            ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                    .getUser();
    return ResponseEntity.ok(tableService.findAllAssigned(waiter));
  }
}
