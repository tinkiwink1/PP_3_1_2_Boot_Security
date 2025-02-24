package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repositories.RoleDao;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
public class AdminController {
    private final UserService userService;
    private final RoleDao roleDao;

    @Autowired
    public AdminController(UserService userService, RoleDao roleDao) {
        this.userService = userService;
        this.roleDao = roleDao;
    }

    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {
        User user = userService.findUserByName(principal.getName());
        model.addAttribute("currentUser", user);
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin";
    }

    @GetMapping("/admin/new_user")
    public ModelAndView newUser(@ModelAttribute("user") User user) {
        ModelAndView mav = new ModelAndView("new_user");
        List<Role> roles = roleDao.getAllRoles();
        mav.addObject("allRoles", roles);
        return mav;
    }

    @PostMapping("/admin/new_user")
    public String saveUser(@ModelAttribute User user,
                           @RequestParam Set<String> selectedRoles) {
        Set<Role> roles = new HashSet<>();
        if (!selectedRoles.isEmpty()) {
            Arrays.stream(selectedRoles.toArray()).forEach(roleName -> roles.add(roleDao.findRoleByName(roleName.toString())));
        }
        user.setRoles(roles);
        userService.saveUser(user.getUsername(), user.getPassword(), user.getEmail(), user.getRoles());
        return "redirect:/admin";
    }

    @GetMapping("/admin/delete_user")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/edit")
    public String editUser(@RequestParam Long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "edit";
    }
    @PostMapping("/admin/edit")
    public String setEdit(@RequestParam Long id, @ModelAttribute User user) {
        userService.updateUser(id, user.getUsername(), user.getPassword(), user.getEmail());
        return "redirect:/admin";
    }

    @PostMapping("/admin/findByID")
    public String findByName(@RequestParam Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findUserByName(userDetails.getUsername());
        model.addAttribute("currentUser", user);
        model.addAttribute("userFound", userService.findUserById(id));
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

}
