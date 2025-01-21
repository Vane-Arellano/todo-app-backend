package com.example.todo_app_backend.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.todo_app_backend.models.Todo;
import com.example.todo_app_backend.repositories.TodoRepository;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository; 

    public List<Todo> getTodos(){
        return todoRepository.getTodos();
    }

    public Optional<Todo> getTodoById(String id){
        return todoRepository.getTodoById(id); 
    }

    public Todo createTodo(Todo todo){
        return todoRepository.createTodo(todo);
    }

    public Todo updateTodo(String id, Todo todo){
        return todoRepository.updateTodo(id, todo);
    }
    
    public boolean deleteTodo(String id){
        return todoRepository.deleteTodo(id);
    }

    public void markStatus(String id){
        Optional<Todo> todo = todoRepository.getTodoById(id);
        if (todo.isPresent() && !todo.get().isDone()) {
            // if the todo exists and is not done, mark it as done 
            todoRepository.markStatus(id, LocalDateTime.now());
        }
        else if(todo.isPresent() && todo.get().isDone()){
            // if its done then its marked as undone and set the done date to null
            todoRepository.markStatus(id, null);
        }
    }

    public Map<String, String> getMetrics(){
        Map<String, String> metrics = todoRepository.getMetrics(); 
        return metrics; 
    }
}
