package com.cadence.controller;

import java.util.List;

import com.cadence.entity.Habit;
import com.cadence.repository.HabitRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/api/habits")
    public Habit createHabit(@RequestBody String name) {
        Habit habit = new Habit(name, null);
        return habitRepository.save(habit);
    }
}