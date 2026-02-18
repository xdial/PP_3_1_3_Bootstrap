package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
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

    @GetMapping("")
    public String admin(Model model) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(userName);
        model.addAttribute("user", user);
        List<User> users = userService.findAll();
        model.addAttribute("allUsers", users);
        model.addAttribute("newUser", new User());
        List<Role> roles = userService.findAllRoles();
        model.addAttribute("roles", roles);
        return "bsadmin";
    }

    //REGISTRATION NEW USER
    @PostMapping("/new")
    public String saveUser(@ModelAttribute("newUser") User user, @RequestParam("roles") List<Long> roles) {
        userService.save(user, roles);
        return "redirect:/admin";
    }

    //ALL USERS
    @GetMapping("/users")
    public String allUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("allUsers", users);
        return "users";
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
        model.addAttribute("userUpdate", userService.findById(id));
        return "admin/edit";
    }

    @PatchMapping("/edit/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("userUpdate") User user,
                             @RequestParam("roles") List<Long> roles) {
        userService.update(id, user, roles);
        return "redirect:/admin";
    }

    //DELETE
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }
}