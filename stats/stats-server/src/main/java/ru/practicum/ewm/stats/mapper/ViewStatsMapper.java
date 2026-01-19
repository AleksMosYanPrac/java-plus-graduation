package ru.practicum.ewm.stats.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.stats.dto.ViewStatsDto;
import ru.practicum.ewm.stats.model.ViewStats;

@Component
public class ViewStatsMapper {

    public ViewStatsDto toViewStatsDto(ViewStats viewStats) {
        return new ViewStatsDto(
                viewStats.getApp(),
                viewStats.getUri(),
                (int) viewStats.getHits());
    }
}
