package ru.practicum.ewm.core.compilations.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.core.compilations.dto.CompilationDto;
import ru.practicum.ewm.core.compilations.dto.NewCompilationDto;
import ru.practicum.ewm.core.compilations.dto.UpdateCompilationRequest;
import ru.practicum.ewm.core.compilations.interfaces.CompilationService;
import ru.practicum.ewm.core.exceptions.ApiErrorHandler;
import ru.practicum.ewm.core.exceptions.DataIntegrityViolation;
import ru.practicum.ewm.core.exceptions.NotFoundException;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
public class AdminCompilationController implements ApiErrorHandler {

    private final CompilationService compilationService;

    @PostMapping
    @ResponseStatus(CREATED)
    public CompilationDto createNewCompilation(@Valid @RequestBody NewCompilationDto compilation)
            throws DataIntegrityViolation {
        return compilationService.createNewCompilation(compilation);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Long compId) throws NotFoundException {
       compilationService.deleteCompilationById(compId);
    }

    @PatchMapping("/{compId}")
    public CompilationDto update(@PathVariable Long compId,
                                 @Valid @RequestBody UpdateCompilationRequest request) throws NotFoundException {
        return compilationService.updateCompilation(compId, request);
    }
}