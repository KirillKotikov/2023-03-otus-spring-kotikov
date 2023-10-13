package ru.kotikov.blog.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.kotikov.blog.Exceptions.DataNotFoundException;
import ru.kotikov.blog.Utils.HandlerUtil;
import ru.kotikov.blog.dtos.UserDto;
import ru.kotikov.blog.services.UserService;
import ru.kotikov.blog.services.impl.PermissionDeniedException;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @GetMapping("/api/v1/user")
    public ResponseEntity<Object> getAllUserList() {
        List<UserDto> userDtoList = userService.getAllUsers();
        return ResponseEntity.ok(userDtoList);
    }

    @GetMapping("/api/v1/user/{login}")
    public ResponseEntity<Object> getUserByLogin(@PathVariable @NotNull String login) {
        return ResponseEntity.ok(userService.getUserDtoByLogin(login));
    }

    @PostMapping("/api/v1/user")
    public ResponseEntity<Object> saveUser(@RequestBody @Valid @NotNull UserDto userDto) {
            return ResponseEntity.ok(userService.saveUser(userDto));
    }

    @PutMapping("/api/v1/user")
    public ResponseEntity<Object> updateUser(
            @RequestBody @Valid @NotNull UserDto userDto) {
        try {
            return ResponseEntity.ok(userService.updateUser(userDto));
        } catch (DataNotFoundException | PermissionDeniedException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @DeleteMapping("/api/v1/user/{login}")
    public ResponseEntity<Object> deleteUserByLogin(@PathVariable @NotNull String login) {
        try {
            userService.deleteUser(login);
            return ResponseEntity.noContent().build();
        } catch (DataNotFoundException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        return HandlerUtil.getErrors(ex);
    }

}
