package com.cadence.controller;

import com.cadence.dto.HabitCategoryAssignmentRequest;
import com.cadence.dto.HabitRequest;
import com.cadence.dto.HabitResponse;
import com.cadence.service.HabitService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habits")
public class HabitController {

    private final HabitService habitService;

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    @GetMapping
    public List<HabitResponse> listHabits() {
        return habitService.getAllHabits();
    }

    @GetMapping("/{id}")
    public HabitResponse getHabit(@PathVariable Long id) {
        return habitService.getHabitById(id);
    }

    @PostMapping
    public HabitResponse createHabit(@Valid @RequestBody HabitRequest request) {
        return habitService.createHabit(request);
    }

    @PutMapping("/{id}")
    public HabitResponse updateHabit(@PathVariable Long id, @Valid @RequestBody HabitRequest request) {
        return habitService.updateHabit(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHabit(@PathVariable Long id) {
        habitService.deleteHabit(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<HabitResponse> searchHabits(@RequestParam String name) {
        return habitService.searchHabits(name);
    }

    @GetMapping("/count")
    public long countHabits() {
        return habitService.countHabits();
    }

    @PutMapping("/{id}/pin")
    public HabitResponse pinHabit(@PathVariable Long id) {
        return habitService.pinHabit(id);
    }

    @PutMapping("/{id}/unpin")
    public HabitResponse unpinHabit(@PathVariable Long id) {
        return habitService.unpinHabit(id);
    }

    @PutMapping("/{id}/archive")
    public HabitResponse archiveHabit(@PathVariable Long id) {
        return habitService.archiveHabit(id);
    }

    @PutMapping("/{id}/restore")
    public HabitResponse restoreHabit(@PathVariable Long id) {
        return habitService.restoreHabit(id);
    }

    @GetMapping("/{id}/streaks/current")
    public int getCurrentStreak(@PathVariable Long id) {
        return habitService.getCurrentStreak(id);
    }

    @GetMapping("/{id}/streaks/longest")
    public int getLongestStreak(@PathVariable Long id) {
        return habitService.getLongestStreak(id);
    }

    @PutMapping("/{habitId}/category")
    public HabitResponse assignCategory(@PathVariable Long habitId,
                                        @RequestBody HabitCategoryAssignmentRequest request) {
        return habitService.assignCategory(habitId, request.getCategoryId());
    }

    @GetMapping("/category/{categoryId}")
    public List<HabitResponse> getHabitsByCategory(@PathVariable Long categoryId) {
        return habitService.getHabitsByCategory(categoryId);
    }
}