package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Set;


public interface UserService extends UserDetailsService {

    public UserDetails loadUserByUsername(String username);

    public User findByUsername(String username);

    public User findById(Long id);

    public User userName();

    public List<User> findAll();

    // REGISTRATION FROM ADMIN PANEL
    public boolean save(User user, Set<Role> roleIds);

    //REGISTRATION
    public boolean save(User user);

    //DELETE USER
    public void deleteById(Long userId);

    //UPDATE USER
    public User update(Long id, User updatedUser, Set<Role> roleIds);

}
