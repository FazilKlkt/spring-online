package com.example.rest_service.dto;

import com.example.rest_service.model.TodoStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Todo response object")
public class TodoResponse {
    @Schema(description = "Unique identifier of the todo", example = "1")
    private Long id;

    @Schema(description = "Title of the todo", example = "Complete project documentation")
    private String title;

    @Schema(description = "Detailed description of the todo", example = "Write API documentation for the todo service")
    private String description;

    @Schema(description = "Current status of the todo", example = "PENDING")
    private TodoStatus status;

    @Schema(description = "Creation timestamp", example = "2024-01-15T10:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Last update timestamp", example = "2024-01-15T14:20:00")
    private LocalDateTime updatedAt;
}
