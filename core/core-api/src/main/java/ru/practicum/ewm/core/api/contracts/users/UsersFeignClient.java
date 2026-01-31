package ru.practicum.ewm.core.api.contracts.users;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "user-service", path = "/api/v1/users", fallback = UsersFeignClientFallback.class)
public interface UsersFeignClient extends UsersFeignContract{
}