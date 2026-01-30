package ru.practicum.ewm.core.categories.interfaces;

import ru.practicum.ewm.core.categories.dto.CategoryDto;
import ru.practicum.ewm.stats.exceptions.DataIntegrityViolation;
import ru.practicum.ewm.stats.exceptions.NotFoundException;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> findAll(int from, int size);

    CategoryDto findById(Long catId) throws NotFoundException;

    CategoryDto addNewCategory(CategoryDto category) throws DataIntegrityViolation;

    void deleteCategoryById(Long catId) throws NotFoundException, DataIntegrityViolation;

    CategoryDto updateCategory(Long catId, CategoryDto category) throws NotFoundException, DataIntegrityViolation;
}