package com.cadence.service;

import com.cadence.dto.HabitRequest;
import com.cadence.dto.HabitResponse;
import com.cadence.entity.Habit;
import com.cadence.exception.ResourceNotFoundException;
import com.cadence.repository.HabitRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HabitService {

    private final HabitRepository habitRepository;

    public HabitService(HabitRepository habitRepository) {
        this.habitRepository = habitRepository;
    }

    public List<HabitResponse> getAllHabits() {
        return habitRepository.findAll().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public HabitResponse getHabitById(Long id) {
        Habit habit = habitRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Habit not found with id: " + id));
        return toResponse(habit);
    }

    public HabitResponse createHabit(HabitRequest request) {
        Habit habit = new Habit(request.getName(), request.getDescription());
        return toResponse(habitRepository.save(habit));
    }

    public HabitResponse updateHabit(Long id, HabitRequest request) {
        Habit habit = habitRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Habit not found with id: " + id));
        habit.setName(request.getName());
        habit.setDescription(request.getDescription());
        return toResponse(habitRepository.save(habit));
    }

    public void deleteHabit(Long id) {
        if (!habitRepository.existsById(id)) {
            throw new ResourceNotFoundException("Habit not found with id: " + id);
        }
        habitRepository.deleteById(id);
    }

    public List<HabitResponse> searchHabits(String name) {
        return habitRepository.findByNameContainingIgnoreCase(name).stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public long countHabits() {
        return habitRepository.count();
    }

    public HabitResponse togglePin(Long id) {
        Habit habit = habitRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Habit not found with id: " + id));
        habit.setPinned(!habit.isPinned());
        return toResponse(habitRepository.save(habit));
    }

    public HabitResponse toggleArchive(Long id) {
        Habit habit = habitRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Habit not found with id: " + id));
        habit.setArchived(!habit.isArchived());
        return toResponse(habitRepository.save(habit));
    }

    private HabitResponse toResponse(Habit habit) {
        return new HabitResponse(
            habit.getId(),
            habit.getName(),
            habit.getDescription(),
            habit.getCreatedAt(),
            habit.isArchived(),
            habit.isPinned()
        );
    }
}
