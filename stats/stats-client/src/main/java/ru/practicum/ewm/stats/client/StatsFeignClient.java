package ru.practicum.ewm.stats.client;

import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "stats-server")
public interface StatsFeignClient  {
}