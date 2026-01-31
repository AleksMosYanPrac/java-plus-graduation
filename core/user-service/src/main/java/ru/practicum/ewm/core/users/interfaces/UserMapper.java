package ru.practicum.ewm.core.users.interfaces;

import ru.practicum.ewm.core.api.contracts.users.dto.UserDto;
import ru.practicum.ewm.core.api.contracts.users.dto.UserShortDto;
import ru.practicum.ewm.core.users.User;

public interface UserMapper {
    UserDto toDto(User user);

    UserShortDto toUserShort(User user);
}