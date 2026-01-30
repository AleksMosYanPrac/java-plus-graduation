package ru.practicum.ewm.stats.statistics.interfaces;

import ru.practicum.ewm.stats.dto.ViewStatsDto;
import ru.practicum.ewm.stats.statistics.ViewStats;

public interface ViewStatsMapper {
    ViewStatsDto toViewStatsDto(ViewStats viewStats);
}