package com.example.rest_service.service;

import com.example.rest_service.dto.TodoRequest;
import com.example.rest_service.dto.TodoResponse;

import java.util.List;

public interface TodoService {
    TodoResponse createTodo(TodoRequest request);
    TodoResponse getTodoById(Long id);
    List<TodoResponse> getAllTodos();
    List<TodoResponse> getTodosByStatus(String status);
    TodoResponse updateTodo(Long id, TodoRequest request);
    TodoResponse updateTodoStatus(Long id, String status);
    void deleteTodo(Long id);
    void deleteAllTodos();
}
