package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller(value = "/springsecurity/admin")
public class AdminController {
    private final UserService adminService;
    @Autowired
    public AdminController(UserService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/")
    public String pageInfoAboutAdmin(Principal principal, Model model) {
        User user = adminService.findUserByName(principal.getName());
        model.addAttribute("user", user);
        return "admin";
    }

    // GET LIST REQUEST
    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<User> users = adminService.getAllUsers();
        model.addAttribute("list", users);
        return "users";
    }
    // CREATE NEW REQUESTS
    @GetMapping("/new_user")
    public String getNewUserForm(@ModelAttribute("user") User user) {
        return "new_user";
    }
    @PostMapping("/new_user")
    public String setNewServer(@ModelAttribute User user) {
        adminService.saveUser(user.getUsername(), user.getPassword(), user.getEmail());
        return "redirect:/users";
    }
    //UPDATE USER REQUESTS
    @GetMapping("/edit")
    public String editUser(@RequestParam Long id, Model model) {
        model.addAttribute("user", adminService.findUserById(id));
        return "edit_user";
    }
    @PostMapping("/edit")
    public String setEdit(@RequestParam Long id, @ModelAttribute User user) {
        adminService.updateUser(id, user.getUsername(), user.getPassword(), user.getEmail());
        return "redirect:/users";
    }
    //DELETE USER REQUEST
    @GetMapping("/delete_user")
    public String deleteUser(@RequestParam Long id) {
        adminService.deleteUser(id);
        return "redirect:/users";
    }


}
