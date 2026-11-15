package com.cadence.service;

import java.util.List;
import java.util.Optional;

import com.cadence.entity.Habit;
import com.cadence.repository.HabitRepository;
import org.springframework.stereotype.Service;

@Service
public class HabitService {

    private final HabitRepository habitRepository;

    public HabitService(HabitRepository habitRepository) {
        this.habitRepository = habitRepository;
    }

    public List<Habit> getAllHabits() {
        return habitRepository.findAll();
    }

    public Habit getHabitById(Long id) {
        Optional<Habit> optional = habitRepository.findById(id);
        return optional.orElse(null);
    }

    public Habit createHabit(String name) {
        Habit habit = new Habit(name, null);
        return habitRepository.save(habit);
    }

    public Habit updateHabit(Long id, String name) {
        Optional<Habit> optional = habitRepository.findById(id);
        if (optional.isPresent()) {
            Habit habit = optional.get();
            habit.setName(name);
            return habitRepository.save(habit);
        }
        return null;
    }

    public void deleteHabit(Long id) {
        habitRepository.deleteById(id);
    }

    public List<Habit> searchHabits(String name) {
        return habitRepository.findByNameContainingIgnoreCase(name);
    }

    public long countHabits() {
        return habitRepository.count();
    }

    public Habit togglePin(Long id) {
        Optional<Habit> optional = habitRepository.findById(id);
        if (optional.isPresent()) {
            Habit habit = optional.get();
            habit.setPinned(!habit.isPinned());
            return habitRepository.save(habit);
        }
        return null;
    }

    public Habit toggleArchive(Long id) {
        Optional<Habit> optional = habitRepository.findById(id);
        if (optional.isPresent()) {
            Habit habit = optional.get();
            habit.setArchived(!habit.isArchived());
            return habitRepository.save(habit);
        }
        return null;
    }
}