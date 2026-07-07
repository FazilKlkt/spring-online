package com.example.rest_service.service;

import com.example.rest_service.dto.TodoRequest;
import com.example.rest_service.dto.TodoResponse;
import com.example.rest_service.model.Todo;
import com.example.rest_service.model.TodoStatus;
import com.example.rest_service.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class TodoServiceImpl implements  TodoService{
    @Autowired
    private TodoRepository todoRepository;


    @Override
    public TodoResponse createTodo(TodoRequest request) {
        Todo todo = new Todo(request.getTitle(), request.getDescription());
        if(request.getStatus()!=null){
            try{
                todo.setStatus(TodoStatus.valueOf(request.getStatus().toUpperCase()));
            }catch (IllegalArgumentException e){
                System.out.println("Line 26: TodoServiceImpl.java");
            }
        }
        Todo savedTodo = todoRepository.save(todo);
        return convertToResponse(savedTodo);
    }

    @Override
    public TodoResponse getTodoById(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(()-> {
            return new RuntimeException("Todo not found with id: " + id);
        });
        return convertToResponse(todo);
    }

    @Override
    public List<TodoResponse> getAllTodos() {
        return todoRepository.findAll().stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public List<TodoResponse> getTodosByStatus(String status) {
        try {
            TodoStatus todoStatus = TodoStatus.valueOf(status.toUpperCase());
            return todoRepository.findByStatus(todoStatus)
                    .stream()
                    .map(this::convertToResponse)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + status);
        }
    }

    @Override
    public TodoResponse updateTodo(Long id, TodoRequest request) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));

        if (request.getTitle() != null) {
            todo.setTitle(request.getTitle());
        }

        if (request.getDescription() != null) {
            todo.setDescription(request.getDescription());
        }

        if (request.getStatus() != null) {
            try {
                todo.setStatus(TodoStatus.valueOf(request.getStatus().toUpperCase()));
            } catch (IllegalArgumentException e) {
                // Ignore invalid status
            }
        }

        todo.preUpdate(); // Update timestamp
        Todo updatedTodo = todoRepository.save(todo);
        return convertToResponse(updatedTodo);
    }

    @Override
    public TodoResponse updateTodoStatus(Long id, String status) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));

        try {
            todo.setStatus(TodoStatus.valueOf(status.toUpperCase()));
            todo.preUpdate();
            Todo updatedTodo = todoRepository.save(todo);
            return convertToResponse(updatedTodo);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + status);
        }
    }

    @Override
    public void deleteTodo(Long id) {
        if (!todoRepository.existsById(id)) {
            throw new RuntimeException("Todo not found with id: " + id);
        }
        todoRepository.deleteById(id);
    }

    @Override
    public void deleteAllTodos() {
        todoRepository.deleteAll();
    }

    // Helper method to convert Entity to Response DTO
    private TodoResponse convertToResponse(Todo todo) {
        return new TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.getStatus(),
                todo.getCreatedAt(),
                todo.getUpdatedAt()
        );
    }
}
