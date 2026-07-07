package com.example.rest_service.repository;

import com.example.rest_service.model.Todo;
import com.example.rest_service.model.TodoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByStatus(TodoStatus status);
    List<Todo> findByTitleContainingIgnoreCase(String title);
}
