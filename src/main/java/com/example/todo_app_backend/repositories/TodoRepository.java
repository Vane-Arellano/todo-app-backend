package com.example.todo_app_backend.repositories;
import org.springframework.stereotype.Repository;
import com.example.todo_app_backend.dtos.TodoDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.Duration;

@Repository
public class TodoRepository {
    private final List<TodoDTO> todos = new ArrayList<>();

    // Function to get all todos 
    public Map<String, Object> getTodosRepository(int page, int size){

        int start = page * size; 
        int end = Math.min(start + size, todos.size());
        List<TodoDTO> paginatedTodos = todos.subList(start, end);
        int totalPages = todos.size() / size;

        if(todos.size() % size != 0){
            totalPages += 1;
        }
        
        Map<String, Object> todosResponse = new HashMap<>();
        todosResponse.put("todos", paginatedTodos);
        todosResponse.put("totalTodos", todos.size());
        todosResponse.put("totalPages", totalPages);



        return todosResponse;
    }

    // Function to get todo by ID 
    public Optional<TodoDTO> getTodoByIdRepository(String id){
        return todos.stream().filter(todo -> todo.getId().equals(id)).findFirst();
    }

    // Create a new todo 
    public TodoDTO createTodoRepository(TodoDTO todo){
        
        todos.add(0, todo);
        return todo;
    }

    // Update a todo 
    public TodoDTO updateTodo(String id, TodoDTO todo){ 
        getTodoByIdRepository(id).ifPresent(oldTodo -> {
            oldTodo.setName(todo.getName());
            oldTodo.setPriority(todo.getPriority()); 
            oldTodo.setDueDate(todo.getDueDate());
        });
        return todo;
    }

    // Delete a todo 
    public boolean deleteTodoRepository(String id) {
        return todos.removeIf(todo -> todo.getId().equals(id));
    }

    // Mark a todo as done 
    public boolean changeStatusRepository(String id, LocalDateTime doneDate){
        Optional<TodoDTO> todoOptional = getTodoByIdRepository(id);

        if (todoOptional.isPresent()) {
            TodoDTO todo = todoOptional.get();
            todo.setDone(!todo.isDone());  
            todo.setDoneDate(doneDate);    

            return true; 
        }

        return false;
        
    }

    public Map<String, String> getMetricsRepository() {
        String all = calculateAllMetrics(); 
        
        String low = calculateMetricsByPriority("low");
        String medium = calculateMetricsByPriority("medium"); 
        String high = calculateMetricsByPriority("high"); 

        Map<String, String> metrics = new HashMap<>();
     
        metrics.put("general", all);
        metrics.put("low", low);
        metrics.put("medium", medium);
        metrics.put("high", high);

        // Return the map
        return metrics;
    }

    private List<TodoDTO> getDoneTodos() {
        return todos.stream().filter(TodoDTO::isDone).collect(Collectors.toList());
    }

    private List<TodoDTO> getDoneTodoByPriority(String priority){
        List<TodoDTO> doneTodos = getDoneTodos(); 
        return doneTodos.stream().filter(todo -> todo.getPriority().equals(priority)).collect(Collectors.toList());
    }

    private String calculateAllMetrics() {
        List<TodoDTO> doneTodos = getDoneTodos(); 
        if (doneTodos.isEmpty()) {
            return "00:00 minutes"; // Return 00:00 if there are no doneTodos
        }

        long totalTime = doneTodos.stream()
            .mapToLong(todo -> Duration.between(todo.getCreationDate(), todo.getDoneDate()).toSeconds()) 
            .sum();
        long averageTime = totalTime / doneTodos.size(); 

        return formatTime(averageTime); 
    }

    private String calculateMetricsByPriority(String priority){
        List<TodoDTO> doneTodos = getDoneTodoByPriority(priority); 
        if (doneTodos.isEmpty()) {
            return "00:00 minutes"; // Return 00:00 if there are no doneTodos
        }

        long totalTime = doneTodos.stream()
            .mapToLong(todo -> Duration.between(todo.getCreationDate(), todo.getDoneDate()).toSeconds()) 
            .sum();
        long averageTime = totalTime / doneTodos.size(); 

        return formatTime(averageTime);

    }

    private String formatTime(double totalSeconds) {
        if (totalSeconds >= 86400) {
            return formatTimeAsDaysHHMM(totalSeconds);

        } else if (totalSeconds >= 3600){
            return formatTimeAsHHMM(totalSeconds);
        } else {
            return formatTimeAsMMSS(totalSeconds);
        }
    }

    private String formatTimeAsMMSS(double totalSeconds) {
        long minutes = (long) totalSeconds / 60;
        long seconds = (long) (totalSeconds % 60);
        
        return String.format("%02d:%02d minutes", minutes, seconds);
    }

    private String formatTimeAsHHMM(double totalSeconds) {
        int hours = (int) (totalSeconds / 3600); // Cast to int
        int minutes = (int) ((totalSeconds % 3600) / 60); // Cast to int
        return String.format("%02d:%02d hours", hours, minutes);
    }

    private String formatTimeAsDaysHHMM(double totalSeconds) {
        int days = (int) (totalSeconds / 86400); // 1 día = 86400 segundos
        int hours = (int) ((totalSeconds % 86400) / 3600); // Resto de segundos dividido entre 3600 para obtener horas
        int minutes = (int) ((totalSeconds % 3600) / 60); // Resto de segundos dividido entre 60 para obtener minutos
        return String.format("%d %02d:%02d days", days, hours, minutes);
    }

    


}
