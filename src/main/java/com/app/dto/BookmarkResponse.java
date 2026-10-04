package com.app.dto;

import java.time.LocalDateTime;

public record BookmarkResponse(
        String id,
        String title,
        String url,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}