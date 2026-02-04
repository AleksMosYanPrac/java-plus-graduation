package ru.practicum.ewm.core.compilations.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.core.api.contracts.compilations.CompilationAdminContract;
import ru.practicum.ewm.core.api.exceptions.ApiErrorContract;
import ru.practicum.ewm.core.api.exceptions.DataIntegrityViolation;
import ru.practicum.ewm.core.api.exceptions.NotFoundException;
import ru.practicum.ewm.core.api.contracts.compilations.dto.CompilationDto;
import ru.practicum.ewm.core.api.contracts.compilations.dto.NewCompilationDto;
import ru.practicum.ewm.core.api.contracts.compilations.dto.UpdateCompilationRequest;
import ru.practicum.ewm.core.compilations.interfaces.CompilationService;

@RestController
@RequiredArgsConstructor
public class AdminCompilationController implements CompilationAdminContract, ApiErrorContract {

    private final CompilationService compilationService;

    @Override
    public CompilationDto createNewCompilation(NewCompilationDto compilation) throws DataIntegrityViolation {
        return compilationService.createNewCompilation(compilation);
    }

    @Override
    public void delete(Long compId) throws NotFoundException {
        compilationService.deleteCompilationById(compId);
    }

    @Override
    public CompilationDto update(Long compId, UpdateCompilationRequest request) throws NotFoundException {
        return compilationService.updateCompilation(compId, request);
    }
}