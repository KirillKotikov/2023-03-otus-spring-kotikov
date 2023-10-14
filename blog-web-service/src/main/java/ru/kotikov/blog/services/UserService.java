package ru.kotikov.blog.services;

import ru.kotikov.blog.dtos.UserDto;
import ru.kotikov.blog.models.User;

import java.util.List;

public interface UserService {

    String USER = "User";

    String LOGIN = "login";

    boolean userExistsByLogin(String login);

    List<UserDto> getAllUsers();

    UserDto getUserDtoByLogin(String login);

    User getUserByLogin(String login);

    UserDto saveUser(UserDto userDto);

    void deleteUser(String login);

    UserDto updateUser(UserDto userDto);

}
