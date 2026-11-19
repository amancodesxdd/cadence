package com.cadence.dto;

import java.util.List;

public class CategoryResponse {

    private Long id;
    private String name;
    private Long habitCount;

    public CategoryResponse(Long id, String name, Long habitCount) {
        this.id = id;
        this.name = name;
        this.habitCount = habitCount;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public Long getHabitCount() { return habitCount; }
    public void setHabitCount(Long habitCount) { this.habitCount = habitCount; }
}
