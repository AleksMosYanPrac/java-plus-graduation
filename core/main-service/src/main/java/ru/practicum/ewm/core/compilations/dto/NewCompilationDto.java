package ru.practicum.ewm.core.compilations.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Data
public class NewCompilationDto {
    private Long id;
    private List<Long> events = new ArrayList<>();
    private Boolean pinned = Boolean.FALSE;
    @NotBlank
    @Length(min = 1, max = 50)
    private String title;
}