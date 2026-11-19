package com.cadence.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class HabitCompletionRequest {

    @NotNull(message = "Completion date is required")
    private LocalDate completionDate;

    public LocalDate getCompletionDate() { return completionDate; }
    public void setCompletionDate(LocalDate completionDate) { this.completionDate = completionDate; }
}