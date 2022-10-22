package com.softserve.itacademy.controller;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.ToDoService;
import com.softserve.itacademy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/todos")
public class ToDoController {

    @Autowired
    private ToDoService toDoService;
    @Autowired
    private UserService userService;

    @GetMapping("/create/users/{owner_id}")
    public String create(Model model,  @PathVariable(name = "id") Integer id) {
        List<ToDo> todoList = toDoService.getByUserId(id);
        model.addAttribute("user", userService.readById(id));
        model.addAttribute("todos", todoList);
        return "todos-user";
    }

    @PostMapping("/create/users/{owner_id}")
    public String create(Model model,
                         @PathVariable(name = "id") Integer id,
                         @Valid @RequestBody ToDo toDo) {
        toDoService.create(toDo);
        List<ToDo> todos = toDoService.getByUserId(id);
        todos.add(toDo);
        User user = userService.readById(id);
        user.setMyTodos(todos);
        userService.update(user);
        model.addAttribute("todo", toDo);
        return "todos-user";
    }

    @GetMapping("/{id}/tasks")
    public String read(Model model, @PathVariable(name = "id") Integer id) {
        ToDo toDo = toDoService.readById(id);
        model.addAttribute("todo", toDo);
        return "todo-lists";
    }

    @GetMapping("/{todo_id}/update/users/{owner_id}")
    public String update(Model model,
                         @PathVariable(name = "id") Integer id) {
        List<ToDo> todoList = toDoService.getByUserId(id);
        model.addAttribute("user", userService.readById(id));
        model.addAttribute("todos", todoList);
        return "todos-user";
     }

    @PostMapping("/{todo_id}/update/users/{owner_id}")
    public String update(Model model,
                         @PathVariable(name = "id") Integer todoId,
                         @PathVariable(name = "id") Integer ownerId,
                         @Valid @RequestBody ToDo newToDo) {
        try {
            User owner = userService.readById(ownerId);
            ToDo oldToDo = toDoService.readById(todoId);
            oldToDo.setTitle(newToDo.getTitle());
            oldToDo.setCreatedAt(newToDo.getCreatedAt());
            oldToDo.setOwner(owner);
            oldToDo.setTasks(newToDo.getTasks());
            toDoService.update(oldToDo);
        } catch (ConstraintViolationException e) {
            String err = e.getConstraintViolations().stream()
                    .map(m -> m.getMessage()).collect(Collectors.joining());
            model.addAttribute("err", err);
            return "update-todo";
        }
        return "todos-user";
    }

    @GetMapping("/{todo_id}/delete/users/{owner_id}")
    public String delete(Model model,
                         @PathVariable(name = "id") Integer todoId,
                         @PathVariable(name = "id") Integer ownerId) {
        toDoService.delete(todoId);
        return "todos-user";
    }

    @GetMapping("/all/users/{user_id}")
    public String getAll(Model model, @PathVariable(name = "id") Integer id) {
        List<ToDo> allUserToDos = toDoService.getByUserId(id);
        model.addAttribute("todos", allUserToDos);
        return "todos-user";
    }

    @GetMapping("/{id}/add")
    public String addCollaborator(@PathVariable(name = "id") Integer id,
                                  @ModelAttribute(name = "user") User user) {
        ToDo toDo = toDoService.readById(id);
        List<User> collaborators = toDo.getCollaborators();
        collaborators.add(user);
        toDo.setCollaborators(collaborators);
        toDoService.update(toDo);
        return "todo-tasks";
    }

    @GetMapping("/{id}/remove")
    public String removeCollaborator(@PathVariable(name = "id") Integer id,
                                     @ModelAttribute(name = "user") User user) {
        ToDo toDo = toDoService.readById(id);
        List<User> collaborators = toDo.getCollaborators();
        collaborators.remove(user);
        toDo.setCollaborators(collaborators);
        toDoService.update(toDo);
        return "todo-tasks";
    }
}
