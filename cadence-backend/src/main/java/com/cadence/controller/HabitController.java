package com.cadence.controller;

import java.util.List;
import java.util.Optional;

import com.cadence.entity.Habit;
import com.cadence.repository.HabitRepository;
import org.springframework.web.bind.annotation.*;

@RestController
public class HabitController {

    private final HabitRepository habitRepository;

    public HabitController(HabitRepository habitRepository) {
        this.habitRepository = habitRepository;
    }

    @GetMapping("/api/habits")
    public List<Habit> listHabits() {
        return habitRepository.findAll();
    }

    @GetMapping("/api/habits/{id}")
    public Habit getHabit(@PathVariable Long id) {
        Optional<Habit> optional = habitRepository.findById(id);
        return optional.orElse(null);
    }

    @PostMapping("/api/habits")
    public Habit createHabit(@RequestBody String name) {
        Habit habit = new Habit(name, null);
        return habitRepository.save(habit);
    }

    @PutMapping("/api/habits/{id}")
    public Habit updateHabit(@PathVariable Long id, @RequestBody String name) {
        Optional<Habit> optional = habitRepository.findById(id);
        if (optional.isPresent()) {
            Habit habit = optional.get();
            habit.setName(name);
            return habitRepository.save(habit);
        }
        return null;
    }

    @DeleteMapping("/api/habits/{id}")
    public void deleteHabit(@PathVariable Long id) {
        habitRepository.deleteById(id);
    }
    @PutMapping("/api/habits/{id}/pin")
    public Habit pinHabit(@PathVariable Long id) {
        Optional<Habit> optional = habitRepository.findById(id);
        if (optional.isPresent()) {
            Habit habit = optional.get();
            habit.setPinned(!habit.isPinned());  // toggle
            return habitRepository.save(habit);
        }
        return null;
    }

    @PutMapping("/api/habits/{id}/archive")
    public Habit archiveHabit(@PathVariable Long id) {
        Optional<Habit> optional = habitRepository.findById(id);
        if (optional.isPresent()) {
            Habit habit = optional.get();
            habit.setArchived(!habit.isArchived());  // toggle
            return habitRepository.save(habit);
        }
        return null;
    }
    @GetMapping("/api/habits/search")
    public List<Habit> searchHabits(@RequestParam String name) {
        return habitRepository.findByNameContainingIgnoreCase(name);
    }

    @GetMapping("/api/habits/count")
    public long countHabits() {
        return habitRepository.count();
    }
}