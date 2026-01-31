package ru.practicum.ewm.core.users.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.stats.exceptions.ApiErrorHandler;
import ru.practicum.ewm.stats.exceptions.DataIntegrityViolation;
import ru.practicum.ewm.stats.exceptions.NotFoundException;
import ru.practicum.ewm.core.users.dto.NewUserRequest;
import ru.practicum.ewm.core.users.dto.UserDto;
import ru.practicum.ewm.core.users.interfaces.UserService;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUserController implements ApiErrorHandler {

    private final UserService service;

    @GetMapping
    @ResponseStatus(OK)
    public List<UserDto> getUserList(@RequestParam(required = false) Long[] ids,
                             @RequestParam(defaultValue = "0") int from,
                             @RequestParam(defaultValue = "10") int size) {
        return service.findUsersByIds(ids, from, size);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public UserDto addNewUser(@Valid @RequestBody NewUserRequest request) throws DataIntegrityViolation {
        return service.addNewUser(request);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(NO_CONTENT)
    public void deleteUserById(@PathVariable Long userId) throws NotFoundException {
        service.deleteUserById(userId);
    }
}