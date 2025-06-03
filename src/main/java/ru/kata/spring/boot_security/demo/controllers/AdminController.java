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
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String admin(Model model, Principal principal) {
        model.addAttribute("currentUser", userService.findUserByName(principal.getName()));
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    @GetMapping("/new_user")
    public ModelAndView newUser(@ModelAttribute("user") User user) {
        ModelAndView mav = new ModelAndView("new_user");
        mav.addObject("allRoles", roleService.getRoles());
        return mav;
    }

    @PostMapping("/new_user")
    public ModelAndView saveUser(@ModelAttribute User user,
                           @RequestParam Set<String> selectedRoles) {
        ModelAndView mav = new ModelAndView("redirect:/admin");
        Set<Role> roles = roleService.selectRoles(selectedRoles);
        user.setRoles(roles);
        userService.saveUser(user.getUsername(), user.getPassword(), user.getEmail(), user.getRoles());
        return mav;
    }

    @GetMapping("/delete_user")
    public ModelAndView deleteUser(@RequestParam Long id) {
        ModelAndView mav = new ModelAndView("redirect:/admin");
        userService.deleteUser(id);
        return mav;
    }

    @GetMapping("/edit")
    public ModelAndView editUser(@RequestParam Long id, Model model) {
        ModelAndView mav = new ModelAndView("edit");
        model.addAttribute("user", userService.findUserById(id));
        return mav;
    }
    @PostMapping("/edit")
    public ModelAndView setEdit(@RequestParam Long id, @ModelAttribute User user) {
        ModelAndView mav = new ModelAndView("redirect:/admin");
        userService.updateUser(id, user.getUsername(), user.getPassword(), user.getEmail());
        return mav;
    }

    @PostMapping("/findByID")
    public ModelAndView findByName(@RequestParam Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        ModelAndView mav = new ModelAndView("admin");
        User user = userService.findUserByName(userDetails.getUsername());
        model.addAttribute("currentUser", user);
        model.addAttribute("userFound", userService.findUserById(id));
        model.addAttribute("users", userService.getAllUsers());
        return mav;
    }

}
