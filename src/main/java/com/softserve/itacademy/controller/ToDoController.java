package com.softserve.itacademy.controller;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.ToDoService;
import com.softserve.itacademy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/todos")
public class ToDoController {

    @Autowired
    private ToDoService toDoService;
    @Autowired
    private UserService userService;

    //add needed fields

    @GetMapping("/create/users/{owner_id}")
    public String create(@PathVariable(name = "id") Integer id) {
        return "create-todo";
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
        return "todo-lists";
    }

    @GetMapping("/{id}/tasks")
    public String read(Model model, @PathVariable(name = "id") Integer id) {
        ToDo toDo = toDoService.readById(id);
        model.addAttribute("todo", toDo);
        return "todo-lists";
    }

    //@GetMapping("/{todo_id}/update/users/{owner_id}")
    //public String update(//add needed parameters) {
    //    return " ";
    // }
    //
//    @PostMapping("/{todo_id}/update/users/{owner_id}")
//    public String update(//add needed parameters) {
//        //ToDo
//        return " ";
//    }
//
//    @GetMapping("/{todo_id}/delete/users/{owner_id}")
//    public String delete(//add needed parameters) {
//                         // ToDo
//        return " ";
//    }
//
    @GetMapping("/all/users/{user_id}")
    public String getAll(Model model, @PathVariable(name = "id") Integer id) {
        List<ToDo> allUserToDos = toDoService.getByUserId(id);
        model.addAttribute("todos", allUserToDos);
        return "todos-user";
    }
//
//    @GetMapping("/{id}/add")
//    public String addCollaborator(//add needed parameters) {
//        //ToDo
//        return " ";
//    }
//
//    @GetMapping("/{id}/remove")
//    public String removeCollaborator(//add needed parameters) {
//        //ToDo
//        return " ";
//    }
}
