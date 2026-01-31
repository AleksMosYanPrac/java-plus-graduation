package ru.practicum.ewm.stats.contracts;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.stats.dto.EndpointHitDto;
import ru.practicum.ewm.stats.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

public interface StatsContract {

    @PostMapping("/hit")
    @ResponseStatus(CREATED)
    EndpointHitDto post(@RequestBody EndpointHitDto requestBody);

    @GetMapping("/stats")
    List<ViewStatsDto> get(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                           @RequestParam(defaultValue = "") String[] uris,
                           @RequestParam(defaultValue = "false") boolean unique);
}