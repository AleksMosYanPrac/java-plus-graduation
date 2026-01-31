package ru.practicum.ewm.core.users;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.core.users.dto.UserDto;
import ru.practicum.ewm.core.users.interfaces.UserMapper;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        return userDto;
    }
}