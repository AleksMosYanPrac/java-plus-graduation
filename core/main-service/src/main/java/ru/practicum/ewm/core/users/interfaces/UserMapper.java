package ru.practicum.ewm.core.users.interfaces;

import ru.practicum.ewm.core.users.User;
import ru.practicum.ewm.core.users.dto.UserDto;

public interface UserMapper {
    UserDto toDto(User user);
}