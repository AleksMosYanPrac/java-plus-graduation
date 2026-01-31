package ru.practicum.ewm.core.api.contracts.categories;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.core.api.contracts.categories.dto.CategoryDto;
import ru.practicum.ewm.core.api.exceptions.DataIntegrityViolation;
import ru.practicum.ewm.core.api.exceptions.NotFoundException;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RequestMapping("/admin/categories")
public interface CategoryAdminContract {

    @PostMapping
    @ResponseStatus(CREATED)
    CategoryDto create(@Valid @RequestBody CategoryDto category) throws DataIntegrityViolation;

    @PatchMapping("/{catId}")
    CategoryDto update(@PathVariable Long catId,
                       @Valid @RequestBody CategoryDto category) throws NotFoundException, DataIntegrityViolation;

    @DeleteMapping("/{catId}")
    @ResponseStatus(NO_CONTENT)
    void delete(@PathVariable Long catId) throws NotFoundException, DataIntegrityViolation;
}