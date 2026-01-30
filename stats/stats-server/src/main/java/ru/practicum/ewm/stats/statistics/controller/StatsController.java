package ru.practicum.ewm.stats.statistics.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.stats.contracts.StatsContract;
import ru.practicum.ewm.stats.dto.EndpointHitDto;
import ru.practicum.ewm.stats.dto.ViewStatsDto;
import ru.practicum.ewm.stats.exceptions.ApiErrorHandler;
import ru.practicum.ewm.stats.statistics.dto.StatRequest;
import ru.practicum.ewm.stats.statistics.interfaces.StatsService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class StatsController implements StatsContract, ApiErrorHandler {

    private final StatsService service;

    @Override
    public EndpointHitDto post(EndpointHitDto requestBody) {
        return service.send(requestBody);
    }

    @Override
    public List<ViewStatsDto> get(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique) {
        StatRequest statRequest = new StatRequest();
        statRequest.setRangeStart(start);
        statRequest.setRangeEnd(end);
        statRequest.setUris(uris);
        statRequest.setIsUnique(unique);
        return service.receive(statRequest);
    }
}