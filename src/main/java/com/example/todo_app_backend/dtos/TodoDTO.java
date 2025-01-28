package com.example.todo_app_backend.dtos;
import java.util.Optional;
import java.util.UUID;

import java.time.LocalDateTime;

public class TodoDTO {
    private String id; 
    private String name; 
    private LocalDateTime dueDate; 
    private boolean done; 
    private LocalDateTime doneDate; 
    private String priority; 
    private LocalDateTime creationDate; 

    public TodoDTO(){
        this.id = UUID.randomUUID().toString();
        this.creationDate = LocalDateTime.now();
    }

    public TodoDTO(String name, String priority, Optional<LocalDateTime> dueDate){
        this.name = name;
        this.dueDate = dueDate.orElse(null); 
        this.priority = priority;
        this.done = false;
        this.doneDate = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public LocalDateTime getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(LocalDateTime doneDate) {
        this.doneDate = doneDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

}
