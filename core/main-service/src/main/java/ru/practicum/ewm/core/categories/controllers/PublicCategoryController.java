package ru.practicum.ewm.core.categories.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.core.categories.dto.CategoryDto;
import ru.practicum.ewm.core.categories.interfaces.CategoryService;
import ru.practicum.ewm.core.exceptions.ApiErrorHandler;
import ru.practicum.ewm.core.exceptions.NotFoundException;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class PublicCategoryController implements ApiErrorHandler {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> findAll(@RequestParam(defaultValue = "0") int from,
                                     @RequestParam(defaultValue = "10") int size) {
        return categoryService.findAll(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryDto getById(@PathVariable Long catId) throws NotFoundException {
        return categoryService.findById(catId);
    }
}