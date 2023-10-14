package ru.kotikov.blog.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kotikov.blog.Utils.ErrorUtil;
import ru.kotikov.blog.Utils.UserUtils;
import ru.kotikov.blog.dtos.UserDto;
import ru.kotikov.blog.mappers.UserMapper;
import ru.kotikov.blog.models.User;
import ru.kotikov.blog.repositories.UserRepository;
import ru.kotikov.blog.services.UserService;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public boolean userExistsByLogin(String login) {
        return userRepository.existsByLogin(login);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> userList = userRepository.findAll();
        if (!userList.isEmpty()) {
            return userList.stream()
                    .map(userMapper::userToUserDto)
                    .toList();
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public UserDto getUserDtoByLogin(String login) {
        User user = userRepository.findByLogin(login);
        if (user != null) {
            return userMapper.userToUserDto(user);
        } else {
            return null;
        }
    }

    @Override
    public User getUserByLogin(String login) {
        User userByLogin = userRepository.findByLogin(login);
        if (userByLogin == null) {
            ErrorUtil.notExistsBy(USER, LOGIN, login);
        }
        return userByLogin;
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        if (userRepository.existsByLogin(userDto.getLogin())) {
            throw new UserIsExistsException("Пользователь с таким логином уже существует!");
        }
        User user = userMapper.userDtoToUser(userDto);
        user.setAuthorities(List.of("USER"));
        User savedUser = userRepository.save(user);
        return userMapper.userToUserDto(savedUser);
    }

    @Override
    @Transactional
    public UserDto updateUser(UserDto userDto) {
        String loginAuthenticated = UserUtils.getLoginAuthenticated();
        if (!loginAuthenticated.equalsIgnoreCase(userDto.getLogin())
                && !UserUtils.isAdmin()) {
            log.error("User with login {} try change user details with login {}.",
                    loginAuthenticated, userDto.getLogin());
            throw new PermissionDeniedException("You can't change not your account!");
        }
        User userByLogin = userRepository.findByLogin(userDto.getLogin());
        if (userByLogin == null) {
            ErrorUtil.notExistsBy(USER, LOGIN, userDto.getLogin());
        }
        if (userDto.getNickName() != null && !userDto.getNickName().isBlank()) {
            userByLogin.setNickName(userDto.getNickName());
        }
        if (userDto.getPassword() != null && userDto.getPassword().isBlank()) {
            userByLogin.setPassword(userDto.getPassword());
        }
        return userMapper.userToUserDto(userByLogin);
    }

    @Override
    public void deleteUser(String login) {
        int rowsAffected = userRepository.deleteUserByLogin(login);
        if (rowsAffected == 0) {
            ErrorUtil.notExistsBy(USER, LOGIN, login);
        }
    }

}
