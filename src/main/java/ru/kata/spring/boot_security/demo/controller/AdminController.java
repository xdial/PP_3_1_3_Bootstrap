package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller()
@RequestMapping("/admin")
public class AdminController {

    UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    //ALL USERS
    @GetMapping("/users")
    public String allUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("allUsers", users);
        return "/users";
    }


    // SHOW USER
    @GetMapping("/{id}")
    public String showById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("showUser", userService.findById(id));
        return "showById";
    }

    //UPDATE
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "edit";
    }

    @PatchMapping("/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") User user) {
        userService.update(id, user);
        return "redirect:/admin/users";
    }

    //DELETE
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }
}