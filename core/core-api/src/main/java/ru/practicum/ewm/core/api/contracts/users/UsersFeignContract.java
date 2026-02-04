package ru.practicum.ewm.core.api.contracts.users;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.practicum.ewm.core.api.contracts.users.dto.UserShortDto;
import ru.practicum.ewm.core.api.exceptions.NotFoundException;

public interface UsersFeignContract {
    @GetMapping("/feign/{userId}")
    UserShortDto findById(@PathVariable Long userId) throws NotFoundException;
}