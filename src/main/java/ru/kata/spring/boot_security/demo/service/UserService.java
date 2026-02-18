package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {


    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, @Lazy BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //    @Override
//    @Transactional
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        User user = userRepository.findByUsername(username);
//        if (user == null) {
//            throw new UsernameNotFoundException(String.format("User with name: '%s' not found", username));
//        }
//        Set<? extends GrantedAuthority> authorities = user.getRoles().stream()
//                .map((role) -> new SimpleGrantedAuthority(role.getName()))
//                .collect(Collectors.toSet());
//
//        return new org.springframework.security.core.userdetails.User(
//                user.getUsername(),
//                user.getPassword(),
//                authorities
//        );
//    }
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User with email: '%s' not found", email));
        }
        Set<? extends GrantedAuthority> authorities = user.getRoles().stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(new User());
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    // REGISTRATION FROM ADMIN PANEL
    @Transactional
    public boolean save(User user, List<Long> roleIds) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null) {
            return false;
        }
        Set<Role> roles = roleIds.stream()
                .map(id -> roleRepository.findById(id).orElse(null)).collect(Collectors.toSet());
        user.setUsername(user.getUsername());
        user.setLastname(user.getLastname());
        user.setEmail(user.getEmail());
        user.setAge(user.getAge());
        user.setPhoneNumber(user.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roles);
        userRepository.save(user);
        return true;
    }

    //REGISTRATION
    @Transactional
    public boolean save(User user) {
        User userFromDB = userRepository.findByEmail(user.getEmail());
        if (userFromDB != null) {
            return false;
        }
        user.setUsername(user.getUsername());
        user.setLastname(user.getLastname());
        user.setEmail(user.getEmail());
        user.setAge(user.getAge());
        user.setPhoneNumber(user.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(roleRepository.getById(1L)));
        userRepository.save(user);
        return true;
    }

    public void deleteById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with name: '%s' not found", findById(userId).getUsername())));

        user.getRoles().clear();
        userRepository.delete(user);
    }

    @Transactional
    public User update(Long id, User updatedUser, List<Long> roleIds) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setLastname(updatedUser.getLastname());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setAge(updatedUser.getAge());
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        Set<Role> roles = roleIds.stream()
                .map(ids -> roleRepository.findById(ids).orElse(null)).collect(Collectors.toSet());
        existingUser.setRoles(roles);
        return userRepository.save(existingUser);
    }

}