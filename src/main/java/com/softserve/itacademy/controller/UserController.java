package com.softserve.itacademy.controller;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

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
    public String addUser(@ModelAttribute(name = "user") User user) {
        userService.create(user);
        return "redirect:/"; //todo redirect to todo-lists
    }

    @GetMapping("/read/{id}")
    public String read(Model model, @PathVariable(name = "id") Integer id) {
        User user = userService.readById(id);
        model.addAttribute("user", user);
        return "update-user";
    }

//
//    @GetMapping("/{id}/update")
//    public String update(//add needed parameters) {
//        //ToDo
//        return " ";
//    }
//
//
//
//    @GetMapping("/all")
//    public String getAll(//add needed parameters) {
//        //ToDo
//        return " ";
//    }
}
