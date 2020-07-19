package com.example.cafemanagement.service;

import com.example.cafemanagement.dto.TableDTO;
import com.example.cafemanagement.model.Role;
import com.example.cafemanagement.model.Table;
import com.example.cafemanagement.model.User;
import com.example.cafemanagement.repository.TableRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/** Logic implementation for users. */
@AllArgsConstructor
@Service
public class TableService {
  private static final String[] IGNORE_FIELDS = {"id", "creator", "created"};
  private final TableRepository tableRepository;
  private final UserService userService;

  /**
   * Reads all the tables from repository.
   *
   * @return the list of all tables.
   */
  @NonNull
  public List<Table> findAll() {
    return tableRepository.findAll();
  }

  /**
   * Searches for the table by the given {@code id} in the repository.
   *
   * @param id the table's identifier.
   * @return {@link java.util.Optional} of the table.
   */
  @NonNull
  public Optional<Table> findById(int id) {
    return tableRepository.findById(id);
  }

  /**
   * Creates a table from the provided data.
   *
   * @param tableDTO object describing table.
   * @param creator the creator of the table.
   * @return the created table.
   */
  public Table create(@NonNull TableDTO tableDTO, @NonNull User creator) {
    tableDTO.setId(null);
    Table table = initTable(tableDTO, creator);
    if (table == null) {
      return null;
    }
    return tableRepository.saveAndFlush(table);
  }

  /**
   * Updates a table based on the provided data.
   *
   * @param tableDTO object describing table.
   * @return the updated table.
   */
  @NonNull
  public Optional<Table> update(@NonNull TableDTO tableDTO) {
    Optional<Table> table = tableRepository.findById(tableDTO.getId());
    if (!table.isPresent()) {
      return table;
    }
    Table tableToUpdate = initTable(tableDTO, null);
    if (table == null) {
      return Optional.empty();
    }
    BeanUtils.copyProperties(tableToUpdate, table.get(), IGNORE_FIELDS);
    return Optional.of(tableRepository.saveAndFlush(table.get()));
  }

  /**
   * Deletes a table with the given {@code id}.
   *
   * @param id the table's identifier.
   * @return {@link java.util.Optional} of the deleted table.
   */
  @NonNull
  public Optional<Table> delete(int id) {
    Optional<Table> table = tableRepository.findById(id);
    if (table.isPresent()) {
      tableRepository.deleteById(id);
    }
    return table;
  }

  /**
   * Reads all tables assigned to the {@code waiter}.
   *
   * @param waiter the waiter.
   * @return list of tables assigned to {@code waiter}.
   */
  @NonNull
  public List<Table> findAllAssigned(@NonNull User waiter) {
    return tableRepository.findByAssignedTo_Id(waiter.getId());
  }

  @Nullable
  private Table initTable(@NonNull TableDTO tableDTO, @Nullable User user) {
    Optional<User> assignedTo = Optional.empty();
    if (tableDTO.getAssignedTo() != null) {
      assignedTo = userService.findById(tableDTO.getAssignedTo());
    }
    if (assignedTo.isPresent() && assignedTo.get().getRole().equals(Role.MANAGER)) {
      return null;
    }
    Table table = new Table();
    table.setId(tableDTO.getId());
    table.setCreator(user);
    if (assignedTo.isPresent()) {
      table.setAssignedTo(assignedTo.get());
    }
    return table;
  }
}
