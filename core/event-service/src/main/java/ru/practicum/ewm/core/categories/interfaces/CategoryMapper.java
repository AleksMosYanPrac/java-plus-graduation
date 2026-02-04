package ru.practicum.ewm.core.categories.interfaces;

import ru.practicum.ewm.core.categories.Category;
import ru.practicum.ewm.core.api.contracts.categories.dto.CategoryDto;

public interface CategoryMapper {
    CategoryDto toDto(Category category);
}