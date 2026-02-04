package ru.practicum.ewm.core.api.contracts.requests;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "request-service", path = "/api/v1/requests", fallback = RequestsFeignClientFallback.class)
public interface RequestsFeignClient extends RequestsFeignContract{
}