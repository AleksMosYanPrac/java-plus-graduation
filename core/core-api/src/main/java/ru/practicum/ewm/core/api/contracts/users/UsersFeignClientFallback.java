package ru.practicum.ewm.core.api.contracts.users;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.core.api.contracts.users.dto.UserShortDto;
import ru.practicum.ewm.core.api.exceptions.NotFoundException;

@Component
public class UsersFeignClientFallback implements UsersFeignClient {
    @Override
    public UserShortDto findById(Long userId) throws NotFoundException {
        return null;
    }
}