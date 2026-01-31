package ru.practicum.ewm.core.compilations.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.core.compilations.dto.CompilationDto;
import ru.practicum.ewm.core.compilations.interfaces.CompilationService;
import ru.practicum.ewm.stats.exceptions.ApiErrorHandler;
import ru.practicum.ewm.stats.exceptions.NotFoundException;

import java.util.List;

@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
public class PublicCompilationController implements ApiErrorHandler {

    private final CompilationService service;

    @GetMapping
    public List<CompilationDto> getAll(@RequestParam(required = false) Boolean pinned,
                                       @RequestParam(required = false, defaultValue = "0") int from,
                                       @RequestParam(required = false, defaultValue = "10") int size) {
        return service.findAllByPinned(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto getById(@PathVariable Long compId) throws NotFoundException {
        return service.getById(compId);
    }
}