package com.example.todo_app_backend.repositories;
import org.springframework.stereotype.Repository;

import com.example.todo_app_backend.models.Todo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.time.Duration;

@Repository
public class TodoRepository {
    private final List<Todo> todos = new ArrayList<>();

    // Function to get all todos 
    public List<Todo> getTodos(){
        return todos;
    }

    // Function to get todo by ID 
    public Optional<Todo> getTodoById(String id){
        return todos.stream().filter(todo -> todo.getId().equals(id)).findFirst();
    }

    // Create a new todo 
    public Todo createTodo(Todo todo){
        todos.add(todo);
        return todo;
    }

    // Update a todo 
    public Todo updateTodo(String id, Todo todo){ 
        getTodoById(id).ifPresent(oldTodo -> {
            oldTodo.setName(todo.getName());
            oldTodo.setPriority(todo.getPriority()); 
            oldTodo.setDueDate(todo.getDueDate());
        });
        return todo;
    }

    // Delete a todo 
    public boolean deleteTodo(String id) {
        return todos.removeIf(todo -> todo.getId().equals(id));
    }

    // Mark a todo as done 
    public void markStatus(String id, LocalDateTime doneDate){
        getTodoById(id).ifPresent(todo -> {
            todo.setDone(!todo.isDone());
            todo.setDoneDate(doneDate);
        });
    }

    public Map<String, String> getMetrics() {
        double all = calculateAllMetrics(); 
        double low = calculateMetricsByPriority("low");
        double medium = calculateMetricsByPriority("medium"); 
        double high = calculateMetricsByPriority("high"); 

        Map<String, String> metrics = new HashMap<>();
        
        metrics.put("general", String.valueOf(all));
        metrics.put("low", String.valueOf(low));
        metrics.put("medium", String.valueOf(medium));
        metrics.put("high", String.valueOf(high));

        // Return the map
        return metrics;
    }

    private List<Todo> getDoneTodos() {
        return todos.stream().filter(Todo::isDone).collect(Collectors.toList());
    }

    private List<Todo> getDoneTodoByPriority(String priority){
        List<Todo> doneTodos = getDoneTodos(); 
        return doneTodos.stream().filter(todo -> todo.getPriority().equals(priority)).collect(Collectors.toList());
    }

    private double calculateAllMetrics() {
        List<Todo> doneTodos = getDoneTodos(); 
        long totalTime = doneTodos.stream()
            .mapToLong(todo -> Duration.between(todo.getCreationDate(), todo.getDoneDate()).toMinutes()) 
            .sum();
        return doneTodos.isEmpty() ? 0 : totalTime / (double) doneTodos.size();

    }

    private double calculateMetricsByPriority(String priority){
        List<Todo> doneTodos = getDoneTodoByPriority(priority); 
        long totalTime = doneTodos.stream()
            .mapToLong(todo -> Duration.between(todo.getCreationDate(), todo.getDoneDate()).toMinutes()) 
            .sum();

        return doneTodos.isEmpty() ? 0 : totalTime / (double) doneTodos.size();

    }


}
