package com.cadence.controller;

import java.util.List;

import com.cadence.entity.Habit;
import com.cadence.service.HabitService;
import org.springframework.web.bind.annotation.*;

@RestController
public class HabitController {

    private final HabitService habitService;

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    @GetMapping("/api/habits")
    public List<Habit> listHabits() {
        return habitService.getAllHabits();
    }

    @GetMapping("/api/habits/{id}")
    public Habit getHabit(@PathVariable Long id) {
        return habitService.getHabitById(id);
    }

    @PostMapping("/api/habits")
    public Habit createHabit(@RequestBody String name) {
        return habitService.createHabit(name);
    }

    @PutMapping("/api/habits/{id}")
    public Habit updateHabit(@PathVariable Long id, @RequestBody String name) {
        return habitService.updateHabit(id, name);
    }

    @DeleteMapping("/api/habits/{id}")
    public void deleteHabit(@PathVariable Long id) {
        habitService.deleteHabit(id);
    }

    @GetMapping("/api/habits/search")
    public List<Habit> searchHabits(@RequestParam String name) {
        return habitService.searchHabits(name);
    }

    @GetMapping("/api/habits/count")
    public long countHabits() {
        return habitService.countHabits();
    }

    @PutMapping("/api/habits/{id}/pin")
    public Habit pinHabit(@PathVariable Long id) {
        return habitService.togglePin(id);
    }

    @PutMapping("/api/habits/{id}/archive")
    public Habit archiveHabit(@PathVariable Long id) {
        return habitService.toggleArchive(id);
    }
}