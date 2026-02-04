package ru.practicum.ewm.core.compilations.interfaces;

import ru.practicum.ewm.core.api.exceptions.DataIntegrityViolation;
import ru.practicum.ewm.core.api.exceptions.NotFoundException;
import ru.practicum.ewm.core.api.contracts.compilations.dto.CompilationDto;
import ru.practicum.ewm.core.api.contracts.compilations.dto.NewCompilationDto;
import ru.practicum.ewm.core.api.contracts.compilations.dto.UpdateCompilationRequest;

import java.util.List;

public interface CompilationService {
    List<CompilationDto> findAllByPinned(Boolean pinned, int from, int size);

    CompilationDto getById(Long compId) throws NotFoundException;

    CompilationDto createNewCompilation(NewCompilationDto compilation) throws DataIntegrityViolation;

    void deleteCompilationById(Long compId) throws NotFoundException;

    CompilationDto updateCompilation(Long compId, UpdateCompilationRequest request) throws NotFoundException;
}