package com.cadence.repository;

import java.util.List;                                    // ADD THIS
import com.cadence.entity.Habit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitRepository extends JpaRepository<Habit, Long> {

    List<Habit> findByNameContainingIgnoreCase(String name);

    long countByCategoryId(Long categoryId);

    List<Habit> findByCategoryId(Long categoryId);

    List<Habit> findByArchived(boolean archived);
}