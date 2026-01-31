package ru.practicum.ewm.stats.statistics.interfaces;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import ru.practicum.ewm.stats.dto.EndpointHitDto;
import ru.practicum.ewm.stats.dto.ViewStatsDto;
import ru.practicum.ewm.stats.statistics.dto.StatRequest;

import java.util.List;

@Validated
public interface StatsService {

    EndpointHitDto send(EndpointHitDto endpointHit);

    List<ViewStatsDto> receive(@Valid StatRequest statRequest);
}