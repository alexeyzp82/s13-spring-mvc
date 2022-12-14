package com.softserve.itacademy.controller;

import com.softserve.itacademy.model.Role;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.RoleService;
import com.softserve.itacademy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/create")
    public String create(@ModelAttribute(name = "user") User user) {
        return "create-user";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id) {
        userService.delete(id);
        return "redirect:/";
    }

    @PostMapping("/create")
    public String addUser(Model model, @ModelAttribute(name = "user") User user) {
        try {
            userService.create(user);
        } catch (ConstraintViolationException e) {
            String err = e.getConstraintViolations().stream()
                    .map(m -> m.getMessage()).collect(Collectors.joining());
            model.addAttribute("err", err);
            return "create-user";
        }
        return "redirect:/"; //todo redirect to todo-lists
    }

    @GetMapping("/read/{id}")
    public String read(Model model, @PathVariable(name = "id") Integer id) {
        User user = userService.readById(id);
        model.addAttribute("user", user);
        return "update-user";
    }

    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable(name = "id") Integer id) {
        User userDB = userService.readById(id);
        model.addAttribute("user", userDB);
        model.addAttribute("roles", roleService.getAll());
        return "update-user";
    }

    @PostMapping("/update/{id}")
    public String update(Model model,
                         @ModelAttribute(name = "user") User user,
                          @PathVariable(name = "id") Integer id) {
        Role role = roleService.getAll().stream()
                .filter(r -> r.getName().equals(user.getRole().getName()))
                .findFirst().get();
        try {
            User userDB = userService.readById(id);
            userDB.setFirstName(user.getFirstName());
            userDB.setLastName(user.getLastName());
            userDB.setEmail(user.getEmail());
            userDB.setPassword(user.getPassword());
            userDB.setRole(role);
            userService.update(userDB);
        } catch (TransactionSystemException e) {
            System.out.println();
            String err =  e.getRootCause().toString();
            model.addAttribute("err", err);
            return "update-user";
        }
        model.addAttribute("users", userService.getAll());
        return "home";
    }

    @GetMapping("/all")
    public String getAll(Model model) {
        List<User> users = userService.getAll();
        model.addAttribute("users", users);
        return "users-list";
    }
}
