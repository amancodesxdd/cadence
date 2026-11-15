package com.cadence.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HabitController {

    @GetMapping("/api/habits")
    public String listHabits() {
        return "no habits yet";
    }

    @PostMapping("/api/habits")
    public String createHabit(@RequestBody String habitName) {
        return "Created habit: " + habitName;
    }
}