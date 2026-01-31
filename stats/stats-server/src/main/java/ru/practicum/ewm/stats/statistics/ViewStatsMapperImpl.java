package ru.practicum.ewm.stats.statistics;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.stats.dto.ViewStatsDto;
import ru.practicum.ewm.stats.statistics.interfaces.ViewStatsMapper;

@Component
public class ViewStatsMapperImpl implements ViewStatsMapper {

    @Override
    public ViewStatsDto toViewStatsDto(ViewStats viewStats) {
        ViewStatsDto viewStatsDto = new ViewStatsDto();
        viewStatsDto.setApp(viewStats.getApp());
        viewStatsDto.setUri(viewStats.getUri());
        viewStatsDto.setHits(viewStats.getHits());
        return viewStatsDto;
    }
}