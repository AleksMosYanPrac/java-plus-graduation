package ru.practicum.ewm.stats.client;

import org.springframework.cloud.openfeign.FeignClient;
import ru.practicum.ewm.stats.contracts.StatsContract;

@FeignClient(name = "stats-server")
public interface StatsClient extends StatsContract {
}