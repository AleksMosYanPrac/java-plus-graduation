package ru.practicum.ewm.core.api.contracts.categories;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.ewm.core.api.contracts.categories.dto.CategoryDto;
import ru.practicum.ewm.core.api.exceptions.NotFoundException;

import java.util.List;

@RequestMapping("/categories")
public interface CategoryPublicContract {

    @GetMapping
    List<CategoryDto> findAll(@RequestParam(defaultValue = "0") int from,
                              @RequestParam(defaultValue = "10") int size);

    @GetMapping("/{catId}")
    CategoryDto getById(@PathVariable Long catId) throws NotFoundException;
}