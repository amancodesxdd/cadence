package com.cadence.controller;

import com.cadence.dto.HabitCompletionRequest;
import com.cadence.dto.HabitCompletionResponse;
import com.cadence.service.HabitCompletionService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/habits/{habitId}/completions")
public class HabitCompletionController {

    private final HabitCompletionService completionService;

    public HabitCompletionController(HabitCompletionService completionService) {
        this.completionService = completionService;
    }

    @PostMapping
    public HabitCompletionResponse markCompleted(
        @PathVariable Long habitId,
        @Valid @RequestBody HabitCompletionRequest request) {
        return completionService.markCompleted(habitId, request);
    }

    @DeleteMapping("/{date}")
    public void undoCompletion(
        @PathVariable Long habitId,
        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        completionService.undoCompletion(habitId, date);
    }

    @GetMapping
    public List<HabitCompletionResponse> getHistory(@PathVariable Long habitId) {
        return completionService.getCompletionHistory(habitId);
    }

    @GetMapping("/{date}/status")
    public boolean isCompletedOn(
        @PathVariable Long habitId,
        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return completionService.isCompletedOn(habitId, date);
    }
}