package com.cadence.repository;

import com.cadence.entity.HabitCompletion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HabitCompletionRepository extends JpaRepository<HabitCompletion, Long> {

    Optional<HabitCompletion> findByHabitIdAndCompletionDate(Long habitId, LocalDate completionDate);

    List<HabitCompletion> findByHabitIdOrderByCompletionDateDesc(Long habitId);

    List<HabitCompletion> findByHabitIdOrderByCompletionDateAsc(Long habitId);

    boolean existsByHabitIdAndCompletionDate(Long habitId, LocalDate completionDate);

    void deleteByHabitIdAndCompletionDate(Long habitId, LocalDate completionDate);
    
    
}