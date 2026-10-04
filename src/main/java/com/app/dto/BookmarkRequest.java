package com.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public record BookmarkRequest(

        @NotBlank(message = "Title is required")
        @Size(max = 200, message = "Title must not exceed 200 characters")
        String title,

        @NotBlank(message = "URL is required")
        @URL(message = "URL must be valid")
        String url,

        @Size(max = 1000, message = "Description must not exceed 1000 characters")
        String description
) {
}
