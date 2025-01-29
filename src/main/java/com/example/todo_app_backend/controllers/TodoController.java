package com.example.todo_app_backend.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo_app_backend.dtos.MetricsDTO;
import com.example.todo_app_backend.dtos.TodoDTO;
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
    public Map<String, Object> getTodos(@RequestParam(defaultValue = "0") int page, 
                                 @RequestParam(defaultValue = "10") int size) {
        return todoService.getTodosService(page, size);
    }

    // Get one single todo 
    @GetMapping("/{id}")
    public Optional<TodoDTO> getTodoById(@PathVariable String id) {
        return todoService.getTodoByIdService(id); 
    }
 
    // Create todo /todos
    @PostMapping("")
    public TodoDTO createTodo(@RequestBody TodoDTO todo) {
        return todoService.createTodoService(todo);
    }

    // Update todo /todos/:id
    @PutMapping("/{id}")
    public TodoDTO updateTodo(@PathVariable String id, @RequestBody TodoDTO todo) {
        return todoService.updateTodoService(id, todo);
    }

    // Update todo status 
    @PutMapping("/{id}/changeStatus")
    public boolean changeTodoStatus(@PathVariable String id) {
        return todoService.changeStatusService(id);
    }

    @DeleteMapping("/{id}")
    public boolean deleteTodo(@PathVariable String id) {
        return todoService.deleteTodoService(id); 
    }

    @GetMapping("/metrics")
    public ResponseEntity<Object> getMetrics() {
        MetricsDTO metrics = todoService.getMetricsService();
        Map<String, MetricsDTO> response = new HashMap<>(); 
        response.put("metrics", metrics); 
        return ResponseEntity.ok(metrics); 
    }
}
