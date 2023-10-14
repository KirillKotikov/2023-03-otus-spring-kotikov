package ru.kotikov.blog.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.kotikov.blog.dtos.UserDto;
import ru.kotikov.blog.services.UserService;
import ru.kotikov.blog.services.impl.UserIsExistsException;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/all")
    public String showAllUsers() {
        return "user/allUsers";
    }

    @GetMapping("/user/registration")
    public String showRegistrationForm(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "user/registration";
    }

    @PostMapping("/user/registration")
    public ModelAndView registrationUser(@ModelAttribute("user") @Valid UserDto userDto) {
        try {
            userService.saveUser(userDto);
        } catch (UserIsExistsException ex) {
            ModelAndView modelAndView = new ModelAndView("user/registration", "user", userDto);
            modelAndView.addObject("error", ex.getMessage());
            return modelAndView;
        }
        return new ModelAndView("main");
    }

}
