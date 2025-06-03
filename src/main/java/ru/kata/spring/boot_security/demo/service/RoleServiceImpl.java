package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleDao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {
    private final RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }
    public List<Role> getRoles() {
        return roleDao.getAllRoles();
    }
    public Set<Role> selectRoles(Set<String> selectedRoles) {
        Set<Role> roles = new HashSet<>();
        if (!selectedRoles.isEmpty()) {
            Arrays.stream(selectedRoles.toArray()).forEach(roleName -> roles.add(roleDao.findRoleByName(roleName.toString())));
        }
        return roles;
    }
}
