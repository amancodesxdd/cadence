package com.cadence.service;

import com.cadence.dto.CategoryRequest;
import com.cadence.dto.CategoryResponse;
import com.cadence.entity.Category;
import com.cadence.exception.ResourceNotFoundException;
import com.cadence.repository.CategoryRepository;
import com.cadence.repository.HabitRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final HabitRepository habitRepository;

    public CategoryService(CategoryRepository categoryRepository,
                           HabitRepository habitRepository) {
        this.categoryRepository = categoryRepository;
        this.habitRepository = habitRepository;
    }

    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        return toResponse(category);
    }

    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = new Category(request.getName());
        return toResponse(categoryRepository.save(category));
    }

    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        category.setName(request.getName());
        return toResponse(categoryRepository.save(category));
    }

    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }

    private CategoryResponse toResponse(Category category) {
        Long habitCount = habitRepository.countByCategoryId(category.getId());
        return new CategoryResponse(category.getId(), category.getName(), habitCount);
    }
}