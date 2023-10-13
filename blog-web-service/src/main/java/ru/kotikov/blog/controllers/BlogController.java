package ru.kotikov.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BlogController {

    @GetMapping("/")
    public String mainPage() {
        System.out.println();
        return "main";
    }

}