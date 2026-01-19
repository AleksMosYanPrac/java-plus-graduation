package ru.practicum.ewm.core.categories.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.core.categories.dto.CategoryDto;
import ru.practicum.ewm.core.categories.interfaces.CategoryService;
import ru.practicum.ewm.core.exceptions.ApiErrorHandler;
import ru.practicum.ewm.core.exceptions.DataIntegrityViolation;
import ru.practicum.ewm.core.exceptions.NotFoundException;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController implements ApiErrorHandler {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(CREATED)
    public CategoryDto create(@Valid @RequestBody CategoryDto category) throws DataIntegrityViolation {
        return categoryService.addNewCategory(category);
    }

    @PatchMapping("/{catId}")
    public CategoryDto update(@PathVariable Long catId,
                              @Valid @RequestBody CategoryDto category) throws NotFoundException, DataIntegrityViolation {
        return categoryService.updateCategory(catId, category);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Long catId) throws NotFoundException, DataIntegrityViolation {
        categoryService.deleteCategoryById(catId);
    }
}