package ru.practicum.ewm.stats.statistics.interfaces;

import ru.practicum.ewm.stats.dto.EndpointHitDto;
import ru.practicum.ewm.stats.statistics.EndpointHit;

public interface EndpointHitMapper {
    EndpointHit toEndpointHit(EndpointHitDto endpointHitDto);

    EndpointHitDto toEndpointHitDto(EndpointHit endpointHit);
}