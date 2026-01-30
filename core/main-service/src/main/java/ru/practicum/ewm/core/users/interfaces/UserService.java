package ru.practicum.ewm.core.users.interfaces;

import ru.practicum.ewm.stats.exceptions.DataIntegrityViolation;
import ru.practicum.ewm.stats.exceptions.NotFoundException;
import ru.practicum.ewm.core.users.dto.NewUserRequest;
import ru.practicum.ewm.core.users.dto.UserDto;

import java.util.List;


public interface UserService {

    List<UserDto> findUsersByIds(Long[] ids, int from, int size);

    UserDto addNewUser(NewUserRequest user) throws DataIntegrityViolation;

    void deleteUserById(Long userId) throws NotFoundException;
}