package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.RoleService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("")
    public ModelAndView admin() {
        ModelAndView modelAndView = new ModelAndView("bsadmin");
        modelAndView.addObject("user", userService.userName());
        modelAndView.addObject("allUsers", userService.findAll());
        modelAndView.addObject("newUser", new User());
        modelAndView.addObject("roles", roleService.findAll());
        return modelAndView;
    }

    //REGISTRATION NEW USER
    @PostMapping("/new")
    public ModelAndView saveUser(@ModelAttribute("newUser") User user,
                                 @RequestParam("roles") List<Long> roles) {
        userService.save(user, roles);
        return new ModelAndView("redirect:/admin");
    }

    //UPDATE
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("admin/edit");
        modelAndView.addObject("userUpdate", userService.findById(id));
        return modelAndView;
    }

    @PatchMapping("edit/{id}")
    public ModelAndView updateUser(@PathVariable("id") Long id, @ModelAttribute("userUpdate") User user,
                                   @RequestParam("roles") List<Long> roles) {
        userService.update(id, user, roles);
        return new ModelAndView("redirect:/admin");
    }

    //DELETE
    @DeleteMapping("/delete/{id}")
    public ModelAndView deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return new ModelAndView("redirect:/admin");
    }
}