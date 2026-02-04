package ru.practicum.ewm.core.api.contracts.compilations;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.ewm.core.api.contracts.compilations.dto.CompilationDto;
import ru.practicum.ewm.core.api.exceptions.NotFoundException;

import java.util.List;

@RequestMapping("/compilations")
public interface CompilationPublicContract {

    @GetMapping
    List<CompilationDto> getAll(@RequestParam(required = false) Boolean pinned,
                                @RequestParam(required = false, defaultValue = "0") int from,
                                @RequestParam(required = false, defaultValue = "10") int size);

    @GetMapping("/{compId}")
    CompilationDto getById(@PathVariable Long compId) throws NotFoundException;
}