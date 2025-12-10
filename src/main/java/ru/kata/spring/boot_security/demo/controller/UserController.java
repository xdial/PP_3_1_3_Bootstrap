package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //User Page
  /*  @GetMapping("/")
    public String user() {
        return "user";
    }*/

    @GetMapping("/{id}")
    public String user(@PathVariable("id") Long id, Model model) {
        model.addAttribute("showUser", userService.findById(id));
        return "user";
    }
}
