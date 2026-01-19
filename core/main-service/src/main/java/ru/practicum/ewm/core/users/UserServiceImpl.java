package ru.practicum.ewm.core.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.core.exceptions.DataIntegrityViolation;
import ru.practicum.ewm.core.exceptions.NotFoundException;
import ru.practicum.ewm.core.users.dto.NewUserRequest;
import ru.practicum.ewm.core.users.dto.UserDto;
import ru.practicum.ewm.core.users.interfaces.UserMapper;
import ru.practicum.ewm.core.users.interfaces.UserService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Override
    public List<UserDto> findUsersByIds(Long[] ids, int from, int size) {
        log.info("Search users by Ids:{} from:{} size:{}", ids, from, size);
        PageRequest page = PageRequest.of(from, size);
        if (ids != null) {
            return userRepository.findAllByIdIn(List.of(ids), page).stream().map(mapper::toDto).toList();
        } else {
            return userRepository.findAll(page).stream().map(mapper::toDto).toList();
        }
    }

    @Override
    @Transactional
    public UserDto addNewUser(NewUserRequest request) throws DataIntegrityViolation {
        log.info("Create new user with Email:{} Name:{}", request.getEmail(), request.getName());
        if (userRepository.existsByName(request.getName())) {
            throw new DataIntegrityViolation("User with requested name alredy exist");
        }
        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setName(request.getName());
        return mapper.toDto(userRepository.save(newUser));
    }

    @Override
    @Transactional
    public void deleteUserById(Long userId) throws NotFoundException {
        log.info("Delete user with Id:{}", userId);
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User not found with Id" + userId);
        }
        userRepository.deleteById(userId);
    }
}