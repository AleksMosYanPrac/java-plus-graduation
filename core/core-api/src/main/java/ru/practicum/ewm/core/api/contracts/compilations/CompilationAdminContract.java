package ru.practicum.ewm.core.api.contracts.compilations;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.core.api.contracts.compilations.dto.CompilationDto;
import ru.practicum.ewm.core.api.contracts.compilations.dto.NewCompilationDto;
import ru.practicum.ewm.core.api.contracts.compilations.dto.UpdateCompilationRequest;
import ru.practicum.ewm.core.api.exceptions.DataIntegrityViolation;
import ru.practicum.ewm.core.api.exceptions.NotFoundException;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RequestMapping("/admin/compilations")
public interface CompilationAdminContract {

    @PostMapping
    @ResponseStatus(CREATED)
    CompilationDto createNewCompilation(@Valid @RequestBody NewCompilationDto compilation)
            throws DataIntegrityViolation;

    @DeleteMapping("/{compId}")
    @ResponseStatus(NO_CONTENT)
    void delete(@PathVariable Long compId)
            throws NotFoundException;

    @PatchMapping("/{compId}")
    CompilationDto update(@PathVariable Long compId, @Valid @RequestBody UpdateCompilationRequest request)
            throws NotFoundException;
}