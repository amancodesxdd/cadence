package com.cadence.service;

import com.cadence.dto.HabitCompletionRequest;
import com.cadence.dto.HabitCompletionResponse;
import com.cadence.entity.Habit;
import com.cadence.entity.HabitCompletion;
import com.cadence.exception.ResourceNotFoundException;
import com.cadence.repository.HabitCompletionRepository;
import com.cadence.repository.HabitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HabitCompletionService {

    private final HabitCompletionRepository completionRepository;
    private final HabitRepository habitRepository;

    public HabitCompletionService(HabitCompletionRepository completionRepository,
                                  HabitRepository habitRepository) {
        this.completionRepository = completionRepository;
        this.habitRepository = habitRepository;
    }

    @Transactional
    public HabitCompletionResponse markCompleted(Long habitId, HabitCompletionRequest request) {
        Habit habit = habitRepository.findById(habitId)
            .orElseThrow(() -> new ResourceNotFoundException("Habit not found with id: " + habitId));

        LocalDate date = request.getCompletionDate();

        // Prevent duplicate completion for same habit + date
        if (completionRepository.existsByHabitIdAndCompletionDate(habitId, date)) {
            throw new IllegalArgumentException("Habit already completed for date: " + date);
        }

        HabitCompletion completion = new HabitCompletion(habit, date, LocalDateTime.now());
        HabitCompletion saved = completionRepository.save(completion);
        return toResponse(saved);
    }

    @Transactional
    public void undoCompletion(Long habitId, LocalDate date) {
        if (!completionRepository.existsByHabitIdAndCompletionDate(habitId, date)) {
            throw new ResourceNotFoundException("No completion found for habit id: " + habitId + " on date: " + date);
        }
        completionRepository.deleteByHabitIdAndCompletionDate(habitId, date);
    }

    public List<HabitCompletionResponse> getCompletionHistory(Long habitId) {
        if (!habitRepository.existsById(habitId)) {
            throw new ResourceNotFoundException("Habit not found with id: " + habitId);
        }
        return completionRepository.findByHabitIdOrderByCompletionDateDesc(habitId).stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public boolean isCompletedOn(Long habitId, LocalDate date) {
        return completionRepository.existsByHabitIdAndCompletionDate(habitId, date);
    }

    private HabitCompletionResponse toResponse(HabitCompletion completion) {
        return new HabitCompletionResponse(
            completion.getId(),
            completion.getHabit().getId(),
            completion.getCompletionDate(),
            completion.getCreatedAt()
        );
    }
}