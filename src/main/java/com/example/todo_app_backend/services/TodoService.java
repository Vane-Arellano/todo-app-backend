package com.example.todo_app_backend.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.todo_app_backend.dtos.MetricsDTO;
import com.example.todo_app_backend.dtos.TodoDTO;
import com.example.todo_app_backend.repositories.TodoRepository;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository; 

    public List<TodoDTO> getTodosService(){
        return todoRepository.getTodosRepository();
    }

    public Optional<TodoDTO> getTodoByIdService(String id){
        return todoRepository.getTodoByIdRepository(id); 
    }

    public TodoDTO createTodoService(TodoDTO todo){
        return todoRepository.createTodoRepository(todo);
    }

    public TodoDTO updateTodoService(String id, TodoDTO todo){
        return todoRepository.updateTodo(id, todo);
    }
    
    public boolean deleteTodoService(String id){
        return todoRepository.deleteTodoRepository(id);
    }

    public boolean changeStatusService(String id){
        Optional<TodoDTO> todo = todoRepository.getTodoByIdRepository(id);
        if (todo.isPresent() && !todo.get().isDone()) {
            return todoRepository.changeStatusRepository(id, LocalDateTime.now());
        }
        else if(todo.isPresent() && todo.get().isDone()){
            return todoRepository.changeStatusRepository(id, null);
        }
        return false;
    }

    public MetricsDTO getMetricsService(){
        Map<String, String> metrics = todoRepository.getMetricsRepository();
        String generalAverage = metrics.get("general"); 
        String lowAverage = metrics.get("low"); 
        String mediumAverage = metrics.get("medium"); 
        String highAverage = metrics.get("high"); 
        MetricsDTO metricsResponse = new MetricsDTO(generalAverage, lowAverage, mediumAverage, highAverage);
        return metricsResponse;
    }
}
