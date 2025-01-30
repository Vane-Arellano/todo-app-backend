package com.example.todo_app_backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.example.todo_app_backend.controllers.TodoController;
import com.example.todo_app_backend.dtos.MetricsDTO;
import com.example.todo_app_backend.dtos.TodoDTO;
import com.example.todo_app_backend.services.TodoService;

@ExtendWith(MockitoExtension.class) 
public class ControllerTodoAppBackendApplicationTests {
    @Mock
    private TodoService todoService;

    @InjectMocks
    private TodoController todoController;

    private TodoDTO sampleTodo;

    @BeforeEach
    void setUp() {
        sampleTodo = new TodoDTO(
            "Test Task", 
            "medium", 
            Optional.of(LocalDateTime.now())
        );
    }

    @Test
    void testGetTodosController() {
        Map<String, Object> mockResponse = new HashMap<>();
        mockResponse.put("todos", List.of(sampleTodo));

        when(todoService.getTodosService(0, 10)).thenReturn(mockResponse);

        Map<String, Object> result = todoController.getTodos(0, 10);

        assertNotNull(result);
        assertEquals(mockResponse, result);
        verify(todoService, times(1)).getTodosService(0, 10);
    }

    @Test
    void testGetTodoById() {
        String mockId = sampleTodo.getId();
        when(todoService.getTodoByIdService(mockId)).thenReturn(Optional.of(sampleTodo));

        Optional<TodoDTO> result = todoController.getTodoById(mockId);

        assertTrue(result.isPresent());
        assertEquals(mockId, result.get().getId());
        verify(todoService, times(1)).getTodoByIdService(mockId);
    }

    @Test
    void testCreateTodo() {
        String mockId = sampleTodo.getId();
        when(todoService.createTodoService(sampleTodo)).thenReturn(sampleTodo);

        TodoDTO result = todoController.createTodo(sampleTodo);

        assertNotNull(result);
        assertEquals(mockId, result.getId());
        verify(todoService, times(1)).createTodoService(sampleTodo);
    }

    @Test
    void testUpdateTodo() {
        String mockId = sampleTodo.getId();
        when(todoService.updateTodoService(mockId, sampleTodo)).thenReturn(sampleTodo);

        TodoDTO result = todoController.updateTodo(mockId, sampleTodo);

        assertNotNull(result);
        assertEquals(mockId, result.getId());
        verify(todoService, times(1)).updateTodoService(mockId, sampleTodo);
    }

    @Test
    void testChangeTodoStatus() {
        String mockId = sampleTodo.getId();

        when(todoService.changeStatusService(mockId)).thenReturn(true);

        boolean result = todoController.changeTodoStatus(mockId);

        assertTrue(result);
        verify(todoService, times(1)).changeStatusService(mockId);
    }

    @Test
    void testDeleteTodo() {
        String mockId = sampleTodo.getId();

        when(todoService.deleteTodoService(mockId)).thenReturn(true);

        boolean result = todoController.deleteTodo(mockId);

        assertTrue(result);
        verify(todoService, times(1)).deleteTodoService(mockId);
    }

    @SuppressWarnings("deprecation")
    @Test
    void testGetMetrics() {
        MetricsDTO mockMetrics = new MetricsDTO(
            "80", 
            "70", 
            "90", 
            "85");
        when(todoService.getMetricsService()).thenReturn(mockMetrics);

        ResponseEntity<Object> response = todoController.getMetrics();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(todoService, times(1)).getMetricsService();
    }
}
