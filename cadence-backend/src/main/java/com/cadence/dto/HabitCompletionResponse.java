package com.cadence.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class HabitCompletionResponse {

    private Long id;
    private Long habitId;
    private LocalDate completionDate;
    private LocalDateTime createdAt;

    public HabitCompletionResponse(Long id, Long habitId, LocalDate completionDate, LocalDateTime createdAt) {
        this.id = id;
        this.habitId = habitId;
        this.completionDate = completionDate;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getHabitId() { return habitId; }
    public void setHabitId(Long habitId) { this.habitId = habitId; }
    public LocalDate getCompletionDate() { return completionDate; }
    public void setCompletionDate(LocalDate completionDate) { this.completionDate = completionDate; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}