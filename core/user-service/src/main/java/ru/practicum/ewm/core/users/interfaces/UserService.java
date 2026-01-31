package ru.practicum.ewm.core.users.interfaces;

import ru.practicum.ewm.core.api.contracts.users.dto.NewUserRequest;
import ru.practicum.ewm.core.api.contracts.users.dto.UserDto;
import ru.practicum.ewm.core.api.contracts.users.dto.UserShortDto;
import ru.practicum.ewm.core.api.exceptions.DataIntegrityViolation;
import ru.practicum.ewm.core.api.exceptions.NotFoundException;

import java.util.List;


public interface UserService {

    List<UserDto> findUsersByIds(Long[] ids, int from, int size);

    UserDto addNewUser(NewUserRequest user) throws DataIntegrityViolation;

    void deleteUserById(Long userId) throws NotFoundException;

    UserShortDto findUserById(Long userId);
}