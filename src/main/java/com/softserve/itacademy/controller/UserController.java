package com.softserve.itacademy.controller;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/create")
    public String create(@ModelAttribute(name="user") User user) {
        return "create-user";
    }

    //    @GetMapping("/{id}/delete")
    @GetMapping("/delete/{userId}")
    public String deleteUser(@PathVariable(name = "userId") Integer id) {
        userService.delete(id);
        return "redirect:/";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute(name="user") User user) {
        userService.create(user);
        return "redirect:/"; //todo redirect to todo-lists
    }

    //add needed fields


//
//    @PostMapping("/create")
//    public String create(//add needed parameters) {
//        //ToDo
//        return " ";
//    }
//
//    @GetMapping("/{id}/read")
//    public String read(//add needed parameters) {
//        //ToDo
//        return " ";
//    }
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
