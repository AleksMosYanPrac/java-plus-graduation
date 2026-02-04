package ru.practicum.ewm.core.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.core.api.contracts.users.UsersAdminContract;
import ru.practicum.ewm.core.api.contracts.users.dto.NewUserRequest;
import ru.practicum.ewm.core.api.contracts.users.dto.UserDto;
import ru.practicum.ewm.core.api.exceptions.ApiErrorContract;
import ru.practicum.ewm.core.api.exceptions.DataIntegrityViolation;
import ru.practicum.ewm.core.api.exceptions.NotFoundException;
import ru.practicum.ewm.core.users.interfaces.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminUserController implements UsersAdminContract, ApiErrorContract {

    private final UserService service;

    @Override
    public List<UserDto> getUserList(Long[] ids, int from, int size) {
        return service.findUsersByIds(ids, from, size);
    }

    @Override
    public UserDto addNewUser(NewUserRequest request) throws DataIntegrityViolation {
        return service.addNewUser(request);
    }

    @Override
    public void deleteUserById(Long userId) throws NotFoundException {
        service.deleteUserById(userId);
    }
}