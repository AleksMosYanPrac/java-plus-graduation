package ru.practicum.ewm.core.api.contracts.users;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.core.api.contracts.users.dto.NewUserRequest;
import ru.practicum.ewm.core.api.contracts.users.dto.UserDto;
import ru.practicum.ewm.core.api.exceptions.DataIntegrityViolation;
import ru.practicum.ewm.core.api.exceptions.NotFoundException;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequestMapping("/admin/users")
public interface UsersAdminContract {

    @GetMapping
    @ResponseStatus(OK)
    List<UserDto> getUserList(@RequestParam(required = false) Long[] ids,
                              @RequestParam(defaultValue = "0") int from,
                              @RequestParam(defaultValue = "10") int size);

    @PostMapping
    @ResponseStatus(CREATED)
    UserDto addNewUser(@Valid @RequestBody NewUserRequest request) throws DataIntegrityViolation;

    @DeleteMapping("/{userId}")
    @ResponseStatus(NO_CONTENT)
    void deleteUserById(@PathVariable Long userId) throws NotFoundException;
}