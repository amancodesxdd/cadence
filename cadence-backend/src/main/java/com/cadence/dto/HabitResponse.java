package com.cadence.dto;

import java.time.LocalDateTime;

public class HabitResponse {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private boolean archived;
    private boolean pinned;

    public HabitResponse(Long id, String name, String description,
                         LocalDateTime createdAt, boolean archived, boolean pinned) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.archived = archived;
        this.pinned = pinned;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public boolean isArchived() { return archived; }
    public boolean isPinned() { return pinned; }
}
