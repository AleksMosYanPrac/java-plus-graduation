package ru.practicum.ewm.core.compilations.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.core.api.contracts.compilations.CompilationPublicContract;
import ru.practicum.ewm.core.api.exceptions.ApiErrorContract;
import ru.practicum.ewm.core.api.exceptions.NotFoundException;
import ru.practicum.ewm.core.api.contracts.compilations.dto.CompilationDto;
import ru.practicum.ewm.core.compilations.interfaces.CompilationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PublicCompilationController implements CompilationPublicContract, ApiErrorContract {

    private final CompilationService service;

    @Override
    public List<CompilationDto> getAll(Boolean pinned, int from, int size) {
        return service.findAllByPinned(pinned, from, size);
    }

    @Override
    public CompilationDto getById(Long compId) throws NotFoundException {
        return service.getById(compId);
    }
}