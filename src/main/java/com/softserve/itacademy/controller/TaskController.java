package com.softserve.itacademy.controller;

import com.softserve.itacademy.model.*;
import com.softserve.itacademy.service.StateService;
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

    @Autowired
    private StateService stateService;

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
    public String update(Model model,
                         @PathVariable(name = "task_id") Long taskId,
                         @PathVariable(name = "todo_id") Long todoId) {
        Task task = taskService.readById(taskId);
        ToDo toDo = toDoService.readById(todoId);
        List<State> states = stateService.getAll();
        model.addAttribute("task", task);
        model.addAttribute("toDo", toDo);
        model.addAttribute("priorities", Priority.values());
        model.addAttribute("states", states);
        return "update-task";
    }

    @PostMapping("/{task_id}/update/todos/{todo_id}")
    public String update(Model model,
                         @ModelAttribute(name = "user") Task task,
                         @PathVariable(name = "todo_id") Long todoId,
                         @PathVariable(name = "task_id") Long taskId) {
        ToDo toDo = toDoService.readById(todoId);
        task.setId(taskId);
        task.setTodo(toDo);
        State state = stateService.getAll().stream()
                .filter(s -> s.getName().equals(task.getState().getName()))
                .findFirst().get();
        task.setState(state);
        taskService.update(task);
        List<Task> tasks = toDo.getTasks();
        model.addAttribute("tasks", tasks);
        model.addAttribute("toDo", toDo);
        return "todo-tasks";
    }

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
