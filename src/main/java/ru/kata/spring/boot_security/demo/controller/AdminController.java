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
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserServiceImpl userServiceImpl;
    private RoleServiceImpl roleServiceImpl;

    @Autowired
    public AdminController(UserServiceImpl userServiceImpl, RoleServiceImpl roleServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.roleServiceImpl = roleServiceImpl;
    }

    @GetMapping
    public ModelAndView admin() {
        ModelAndView modelAndView = new ModelAndView("bsadmin");
        modelAndView.addObject("user", userServiceImpl.userName());
        modelAndView.addObject("allUsers", userServiceImpl.findAll());
        modelAndView.addObject("newUser", new User());
        modelAndView.addObject("roles", roleServiceImpl.findAll());
        return modelAndView;
    }

    //REGISTRATION NEW USER
    @PostMapping("/new")
    public ModelAndView saveUser(@ModelAttribute("newUser") User user,
                                 @RequestParam("roles") List<Long> roles) {
        userServiceImpl.save(user, roleServiceImpl.getRoles(roles));
        return new ModelAndView("redirect:/admin");
    }

    //UPDATE
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("admin/edit");
        modelAndView.addObject("userUpdate", userServiceImpl.findById(id));
        return modelAndView;
    }

    @PatchMapping("edit/{id}")
    public ModelAndView updateUser(@PathVariable("id") Long id, @ModelAttribute("userUpdate") User user,
                                   @RequestParam("roles") List<Long> roles) {
        userServiceImpl.update(id, user, roleServiceImpl.getRoles(roles));
        return new ModelAndView("redirect:/admin");
    }

    //DELETE
    @DeleteMapping("/delete/{id}")
    public ModelAndView deleteUser(@PathVariable("id") Long id) {
        userServiceImpl.deleteById(id);
        return new ModelAndView("redirect:/admin");
    }
}