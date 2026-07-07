package com.example.rest_service.controller;

import com.example.rest_service.dto.TodoRequest;
import com.example.rest_service.dto.TodoResponse;
import com.example.rest_service.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/todos")
@Tag(name = "Todo Management", description = "Endpoints for managing todo items")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @Operation(summary = "Create a new todo", description = "Creates a new todo item with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Todo created successfully",
                    content = @Content(schema = @Schema(implementation = TodoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<TodoResponse> createTodo(@Valid @RequestBody TodoRequest request) {
        TodoResponse response = todoService.createTodo(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all todos", description = "Retrieves all todo items. Can optionally filter by status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved todos",
                    content = @Content(schema = @Schema(implementation = TodoResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<TodoResponse>> getAllTodos(
            @Parameter(description = "Filter todos by status (PENDING, IN_PROGRESS, COMPLETED, CANCELLED)")
            @RequestParam(required = false) String status) {
        if (status != null && !status.isEmpty()) {
            return ResponseEntity.ok(todoService.getTodosByStatus(status));
        }
        return ResponseEntity.ok(todoService.getAllTodos());
    }

    @Operation(summary = "Get a specific todo", description = "Retrieves a todo item by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved todo",
                    content = @Content(schema = @Schema(implementation = TodoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Todo not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TodoResponse> getTodoById(
            @Parameter(description = "ID of the todo to retrieve", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(todoService.getTodoById(id));
    }

    @Operation(summary = "Update a todo", description = "Updates an existing todo item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todo updated successfully",
                    content = @Content(schema = @Schema(implementation = TodoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Todo not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TodoResponse> updateTodo(
            @Parameter(description = "ID of the todo to update", required = true)
            @PathVariable Long id,
            @Valid @RequestBody TodoRequest request) {
        return ResponseEntity.ok(todoService.updateTodo(id, request));
    }

    @Operation(summary = "Update todo status", description = "Updates only the status of a todo item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status updated successfully",
                    content = @Content(schema = @Schema(implementation = TodoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid status value"),
            @ApiResponse(responseCode = "404", description = "Todo not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<TodoResponse> updateTodoStatus(
            @Parameter(description = "ID of the todo to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "New status (PENDING, IN_PROGRESS, COMPLETED, CANCELLED)", required = true)
            @RequestParam String status) {
        return ResponseEntity.ok(todoService.updateTodoStatus(id, status));
    }

    @Operation(summary = "Delete a todo", description = "Deletes a todo item by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Todo deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Todo not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(
            @Parameter(description = "ID of the todo to delete", required = true)
            @PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete all todos", description = "Deletes all todo items")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "All todos deleted successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteAllTodos() {
        todoService.deleteAllTodos();
        return ResponseEntity.noContent().build();
    }
}
