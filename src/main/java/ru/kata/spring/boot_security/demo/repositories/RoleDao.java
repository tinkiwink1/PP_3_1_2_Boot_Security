package ru.kata.spring.boot_security.demo.repositories;

import ru.kata.spring.boot_security.demo.entity.Role;

import java.util.List;

public interface RoleDao {
    Role findRoleByName(String name);

    void saveRole(Role role);

    List<Role> getAllRoles();
}
