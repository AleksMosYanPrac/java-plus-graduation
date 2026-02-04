package ru.practicum.ewm.core.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.core.api.contracts.users.UsersFeignContract;
import ru.practicum.ewm.core.api.contracts.users.dto.UserShortDto;
import ru.practicum.ewm.core.api.exceptions.ApiErrorContract;
import ru.practicum.ewm.core.api.exceptions.NotFoundException;
import ru.practicum.ewm.core.users.interfaces.UserService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserFeignController implements UsersFeignContract, ApiErrorContract {

    private final UserService userService;

    @Override
    public UserShortDto findById(Long userId) throws NotFoundException {
        return userService.findUserById(userId);
    }
}