package ru.practicum.ewm.core.compilations.interfaces;

import ru.practicum.ewm.core.compilations.Compilation;
import ru.practicum.ewm.core.compilations.dto.CompilationDto;

public interface CompilationMapper {
    CompilationDto toDto(Compilation compilation);
}