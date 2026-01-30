package ru.practicum.ewm.stats.statistics;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.stats.dto.EndpointHitDto;
import ru.practicum.ewm.stats.dto.ViewStatsDto;
import ru.practicum.ewm.stats.statistics.interfaces.EndpointHitMapper;
import ru.practicum.ewm.stats.statistics.interfaces.StatsService;
import ru.practicum.ewm.stats.statistics.interfaces.ViewStatsMapper;

import java.time.LocalDateTime;
import java.util.List;

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
    public List<ViewStatsDto> receive(LocalDateTime start, LocalDateTime end, String[] uris, Boolean isUnique) {
        log.info("Resieve get URI:{}", uris);
        List<ViewStats> views;
        if (isUnique) {
            if (uris != null) {
                views = statsRepository.getDistinctByUris(uris, start, end);
            } else {
                views = statsRepository.getDistinctByStartAndEnd(start, end);
            }
        } else {
            if (uris != null) {
                views = statsRepository.getByUris(uris, start, end);
            } else {
                views = statsRepository.getByStartAndEnd(start, end);
            }
        }
        return views.stream().map(viewStatsMapper::toViewStatsDto).toList();
    }
}