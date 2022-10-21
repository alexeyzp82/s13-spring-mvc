package com.softserve.itacademy.controller;

import com.softserve.itacademy.model.Priority;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping({"/create"})
    public String show(Model model, @ModelAttribute(name = "task") Task task) {
        model.addAttribute("priority", Priority.values());
        return "create-task";
    }

    @PostMapping({"/create"})
    public String create(Model model, @ModelAttribute(name = "task") Task task) {
        model.addAttribute("priority", Priority.values());
        return "create-task";
    }

//    @GetMapping("/create/todos/{todo_id}")
//    public String create(//add needed parameters) {
//        //ToDo
//        return " ";
//    }
//
//    @PostMapping("/create/todos/{todo_id}")
//    public String create(//add needed parameters) {
//        //ToDo
//        return " ";
//    }
//
//    @GetMapping("/{task_id}/update/todos/{todo_id}")
//    public String update(//add needed parameters) {
//        //ToDo
//        return " ";
//    }
//
//    @PostMapping("/{task_id}/update/todos/{todo_id}")
//    public String update(//add needed parameters) {
//       //ToDo
//        return " ";
//    }
//
//    @GetMapping("/{task_id}/delete/todos/{todo_id}")
//    public String delete(//add needed parameters) {
//        //ToDo
//        return " ";
//    }
}
