package com.softserve.itacademy.controller;

import com.softserve.itacademy.model.Priority;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.service.TaskService;
import com.softserve.itacademy.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ToDoService toDoService;

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

    @GetMapping("/create/todos/{todo_id}")
    public String create(Model model, @PathVariable(name = "todo_id") Long id) {
        ToDo toDo = toDoService.readById(id);
        List<Task> tasks = toDo.getTasks();
        model.addAttribute("tasks", tasks);
        model.addAttribute("toDo", toDo);
        return "todo-tasks";
    }
//
//    @PostMapping("/create/todos/{todo_id}")
//    public String create(//add needed parameters) {
//        //ToDo
//        return " ";
//    }
//
    @GetMapping("/{task_id}/update/todos/{todo_id}")
    public String update(Model model) {
        //ToDo
        return "update-task";
    }
//
//    @PostMapping("/{task_id}/update/todos/{todo_id}")
//    public String update(//add needed parameters) {
//       //ToDo
//        return " ";
//    }
//
    @GetMapping("/{task_id}/delete/todos/{todo_id}")
    public String delete(Model model,
                         @PathVariable(name = "task_id") Long taskId,
                         @PathVariable(name = "todo_id") Long todoId) {
        taskService.delete(taskId);
        ToDo toDo = toDoService.readById(todoId);
        List<Task> tasks = toDo.getTasks();
        model.addAttribute("tasks", tasks);
        model.addAttribute("toDo", toDo);
        return "todo-tasks";
    }
}
