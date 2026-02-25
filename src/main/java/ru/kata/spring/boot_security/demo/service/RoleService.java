package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.*;

import java.util.List;
import java.util.Set;

public interface RoleService {

    public Role findById(Long id);

    public List<Role> findAll();

    public Set<Role> getRoles(List<Long> roleIds);
}
