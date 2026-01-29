package ru.practicum.ewm.core.categories;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.core.categories.dto.CategoryDto;
import ru.practicum.ewm.core.categories.interfaces.CategoryMapper;

@Component
public class CategoryMapperImpl implements CategoryMapper {
    @Override
    public CategoryDto toDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }
}