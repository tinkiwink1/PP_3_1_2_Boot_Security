package ru.kata.spring.boot_security.demo.repositories;

import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;

public interface UserDao {
    void saveUser(User user);

    void deleteUser(Long id);

    void updateUser(User user);

    User findUserById(Long Id);

    User findUserByName(String username);

    List<User> getAllUsers();

    User findUserWithRolesByUsername(String username);
}

