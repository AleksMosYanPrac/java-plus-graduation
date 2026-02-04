package ru.practicum.ewm.core.categories.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.core.api.contracts.categories.CategoryAdminContract;
import ru.practicum.ewm.core.api.exceptions.ApiErrorContract;
import ru.practicum.ewm.core.api.exceptions.DataIntegrityViolation;
import ru.practicum.ewm.core.api.exceptions.NotFoundException;
import ru.practicum.ewm.core.api.contracts.categories.dto.CategoryDto;
import ru.practicum.ewm.core.categories.interfaces.CategoryService;

@RestController
@RequiredArgsConstructor
public class AdminCategoryController implements CategoryAdminContract, ApiErrorContract {

    private final CategoryService categoryService;

    @Override
    public CategoryDto create(CategoryDto category) throws DataIntegrityViolation {
        return categoryService.addNewCategory(category);
    }

    @Override
    public CategoryDto update(Long catId,
                              CategoryDto category) throws NotFoundException, DataIntegrityViolation {
        return categoryService.updateCategory(catId, category);
    }

    @Override
    public void delete(Long catId) throws NotFoundException, DataIntegrityViolation {
        categoryService.deleteCategoryById(catId);
    }
}