package com.cadence.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryRequest {

    @NotBlank(message = "Category name is required")
    @Size(max = 50, message = "Name must be at most 50 characters")
    private String name;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}