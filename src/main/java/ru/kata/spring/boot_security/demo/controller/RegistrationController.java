package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    //REGISTRATION NEW USER
    @GetMapping
    public ModelAndView addUser(User user) {
        ModelAndView modelAndView = new ModelAndView("registration");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PostMapping
    public ModelAndView saveUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return new ModelAndView("redirect:/login");
    }
}
