package ru.practicum.ewm.core.categories.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CategoryDto {

    private Long id;

    @NotBlank
    @Length(min = 1, max = 50)
    private String name;
}