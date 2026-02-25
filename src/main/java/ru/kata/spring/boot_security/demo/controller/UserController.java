package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userServiceImpl) {

        this.userService= userServiceImpl;
    }

    @GetMapping("")
    public ModelAndView user() {
        ModelAndView modelAndView = new ModelAndView("bsuser");
        User user = userService.userName();
        modelAndView.addObject("showUser", user);
        return modelAndView;
    }
}
