package ru.practicum.ewm.core.categories.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.core.api.contracts.categories.CategoryPublicContract;
import ru.practicum.ewm.core.api.exceptions.ApiErrorContract;
import ru.practicum.ewm.core.api.exceptions.NotFoundException;
import ru.practicum.ewm.core.api.contracts.categories.dto.CategoryDto;
import ru.practicum.ewm.core.categories.interfaces.CategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PublicCategoryController implements CategoryPublicContract, ApiErrorContract {

    private final CategoryService categoryService;

    @Override
    public List<CategoryDto> findAll(int from, int size) {
        return categoryService.findAll(from, size);
    }

    @Override
    public CategoryDto getById(Long catId) throws NotFoundException {
        return categoryService.findById(catId);
    }
}