package ru.practicum.ewm.stats.statistics;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.stats.dto.EndpointHitDto;
import ru.practicum.ewm.stats.dto.ViewStatsDto;
import ru.practicum.ewm.stats.statistics.dto.StatRequest;
import ru.practicum.ewm.stats.statistics.interfaces.EndpointHitMapper;
import ru.practicum.ewm.stats.statistics.interfaces.StatsService;
import ru.practicum.ewm.stats.statistics.interfaces.ViewStatsMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;
    private final ViewStatsMapper viewStatsMapper;
    private final EndpointHitMapper endpointHitMapper;

    @Override
    public EndpointHitDto send(EndpointHitDto endpointHit) {
        log.info("Resieve hit IP:{} URI:{})", endpointHit.getIp(), endpointHit.getUri());
        EndpointHit data = endpointHitMapper.toEndpointHit(endpointHit);
        EndpointHit endpoint = statsRepository.save(data);
        return endpointHitMapper.toEndpointHitDto(endpoint);
    }

    @Override
    public List<ViewStatsDto> receive(StatRequest request) {
        log.info("Resieve get URI:{}", Arrays.stream(request.getUris()).toList());
        List<ViewStats> views;
        if (request.getIsUnique()) {
            if (Objects.nonNull(request.getUris())) {
                views = statsRepository.getDistinctByUris(request.getUris(), request.getRangeStart(), request.getRangeEnd());
            } else {
                views = statsRepository.getDistinctByStartAndEnd(request.getRangeStart(), request.getRangeEnd());
            }
        } else {
            if (Objects.nonNull(request.getUris())) {
                views = statsRepository.getByUris(request.getUris(), request.getRangeStart(), request.getRangeEnd());
            } else {
                views = statsRepository.getByStartAndEnd(request.getRangeStart(), request.getRangeEnd());
            }
        }
        return views.stream().map(viewStatsMapper::toViewStatsDto).toList();
    }
}