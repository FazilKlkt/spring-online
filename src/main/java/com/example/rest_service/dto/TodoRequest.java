package com.example.rest_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Todo creation/update request")
public class TodoRequest {
    @Schema(description = "Title of the todo", example = "Complete project documentation", required = true)
    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must be less than 100 characters")
    private String title;

    @Schema(description = "Detailed description of the todo", example = "Write API documentation for the todo service")
    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

    @Schema(description = "Status of the todo", example = "PENDING", allowableValues = {"PENDING", "IN_PROGRESS", "COMPLETED", "CANCELLED"})
    private String status;

}
