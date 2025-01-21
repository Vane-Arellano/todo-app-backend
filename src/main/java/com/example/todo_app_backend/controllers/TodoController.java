package com.example.todo_app_backend.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.todo_app_backend.models.Todo;

import com.example.todo_app_backend.services.TodoService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoService todoService; 

    // Get todos /todos
    @GetMapping("")
    public List<Todo> getTodos() {
        return todoService.getTodos();
    }

    // Get one single todo 
    @GetMapping("/{id}")
    public Optional<Todo> getTodoById(@PathVariable String id) {
        return todoService.getTodoById(id); 
    }
 
    // Create todo /todos
    @PostMapping("")
    public Todo createTodo(@RequestBody Todo todo) {
        return todoService.createTodo(todo);
    }

    // Update todo /todos/:id
    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable String id, @RequestBody Todo todo) {
        return todoService.updateTodo(id, todo);
    }

    // Update todo status 
    @PutMapping("/{id}/changeStatus")
    public void changeTodoStatus(@PathVariable String id) {
        todoService.markStatus(id);
    }

    @DeleteMapping("/{id}")
    public boolean deleteTodo(@PathVariable String id) {
        return todoService.deleteTodo(id); 
    }

    @GetMapping("/metrics")
    public Map<String, String> getMetrics() {
        return todoService.getMetrics();
    }
    

    
}
