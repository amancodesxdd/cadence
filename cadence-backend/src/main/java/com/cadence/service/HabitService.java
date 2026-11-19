package com.cadence.service;

import com.cadence.dto.HabitRequest;
import com.cadence.dto.HabitResponse;
import com.cadence.entity.Category;
import com.cadence.entity.Habit;
import com.cadence.exception.ResourceNotFoundException;
import com.cadence.repository.CategoryRepository;
import com.cadence.repository.HabitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HabitService {

    private final HabitRepository habitRepository;
    private final CategoryRepository categoryRepository;

    public HabitService(HabitRepository habitRepository,
                        CategoryRepository categoryRepository) {
        this.habitRepository = habitRepository;
        this.categoryRepository = categoryRepository;
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

    @Transactional
    public HabitResponse createHabit(HabitRequest request) {
        Habit habit = new Habit(request.getName(), request.getDescription());
        return toResponse(habitRepository.save(habit));
    }

    @Transactional
    public HabitResponse updateHabit(Long id, HabitRequest request) {
        Habit habit = habitRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Habit not found with id: " + id));
        habit.setName(request.getName());
        habit.setDescription(request.getDescription());
        return toResponse(habitRepository.save(habit));
    }

    @Transactional
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

    public int getCurrentStreak(Long habitId) {
        // Verify habit exists
        habitRepository.findById(habitId)
            .orElseThrow(() -> new ResourceNotFoundException("Habit not found with id: " + habitId));

        // Get completions newest to oldest
        List<HabitCompletion> completions = completionRepository.findByHabitIdOrderByCompletionDateDesc(habitId);

        if (completions.isEmpty()) {
            return 0;
        }

        LocalDate today = LocalDate.now();
        int streak = 0;
        LocalDate expectedDate = today;

        for (HabitCompletion completion : completions) {
            if (completion.getCompletionDate().equals(expectedDate)) {
                streak++;
                expectedDate = expectedDate.minusDays(1);
            } else {
                break;
            }
        }

        return streak;
    }

    public int getLongestStreak(Long habitId) {
        // Verify habit exists
        habitRepository.findById(habitId)
            .orElseThrow(() -> new ResourceNotFoundException("Habit not found with id: " + habitId));

        // Get completions oldest to newest
        List<HabitCompletion> completions = completionRepository.findByHabitIdOrderByCompletionDateAsc(habitId);

        if (completions.isEmpty()) {
            return 0;
        }

        int maxStreak = 0;
        int currentStreak = 0;
        LocalDate previousDate = null;

        for (HabitCompletion completion : completions) {
            LocalDate date = completion.getCompletionDate();
            if (previousDate == null || date.equals(previousDate.plusDays(1))) {
                currentStreak++;
            } else {
                currentStreak = 1;
            }
            maxStreak = Math.max(maxStreak, currentStreak);
            previousDate = date;
        }

        return maxStreak;
    }

    @Transactional
    public HabitResponse pinHabit(Long id) {
        Habit habit = habitRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Habit not found with id: " + id));
        habit.setPinned(true);
        return toResponse(habitRepository.save(habit));
    }

    @Transactional
    public HabitResponse unpinHabit(Long id) {
        Habit habit = habitRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Habit not found with id: " + id));
        habit.setPinned(false);
        return toResponse(habitRepository.save(habit));
    }

    @Transactional
    public HabitResponse archiveHabit(Long id) {
        Habit habit = habitRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Habit not found with id: " + id));
        habit.setArchived(true);
        return toResponse(habitRepository.save(habit));
    }

    @Transactional
    public HabitResponse restoreHabit(Long id) {
        Habit habit = habitRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Habit not found with id: " + id));
        habit.setArchived(false);
        return toResponse(habitRepository.save(habit));
    }

    @Transactional
    public HabitResponse assignCategory(Long habitId, Long categoryId) {
        Habit habit = habitRepository.findById(habitId)
            .orElseThrow(() -> new ResourceNotFoundException("Habit not found with id: " + habitId));

        Category category = null;
        if (categoryId != null) {
            category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
        }

        habit.setCategory(category);
        return toResponse(habitRepository.save(habit));
    }

    public List<HabitResponse> getHabitsByCategory(Long categoryId) {
        // Verify category exists
        categoryRepository.findById(categoryId)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));

        return habitRepository.findByCategoryId(categoryId).stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
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
