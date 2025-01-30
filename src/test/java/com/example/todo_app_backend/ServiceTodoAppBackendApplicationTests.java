package com.example.todo_app_backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
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

import com.example.todo_app_backend.dtos.MetricsDTO;
import com.example.todo_app_backend.dtos.TodoDTO;
import com.example.todo_app_backend.repositories.TodoRepository;
import com.example.todo_app_backend.services.TodoService;

@ExtendWith(MockitoExtension.class) 
public class ServiceTodoAppBackendApplicationTests {
    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    private TodoDTO sampleTodo;

    @BeforeEach
    void setUp() {
        sampleTodo = new TodoDTO("Test Task", "medium", Optional.of(LocalDateTime.now()));
    }

    @Test
    void testGetTodosService() {
        Map<String, Object> mockTodos = new HashMap<>();
        mockTodos.put("todos", List.of(sampleTodo));
        when(todoRepository.getTodosRepository(0, 10)).thenReturn(mockTodos);

        Map<String, Object> result = todoService.getTodosService(0, 10);
        
        assertNotNull(result);
        assertTrue(result.containsKey("todos"));
        verify(todoRepository, times(1)).getTodosRepository(0, 10);
    }

    @Test
    void testGetTodoByIdService() {
        when(todoRepository.getTodoByIdRepository(sampleTodo.getId())).thenReturn(Optional.of(sampleTodo));

        Optional<TodoDTO> result = todoService.getTodoByIdService(sampleTodo.getId());

        assertTrue(result.isPresent());
        assertEquals(sampleTodo.getId(), result.get().getId());
        verify(todoRepository, times(1)).getTodoByIdRepository(sampleTodo.getId());
    }

    @Test
    void testCreateTodoService() {
        when(todoRepository.createTodoRepository(any(TodoDTO.class))).thenReturn(sampleTodo);

        TodoDTO result = todoService.createTodoService(sampleTodo);

        assertNotNull(result);
        assertEquals(sampleTodo.getId(), result.getId());
        verify(todoRepository, times(1)).createTodoRepository(sampleTodo);
    }

    @Test
    void testUpdateTodoService() {
        when(todoRepository.updateTodo(eq(sampleTodo.getId()), any(TodoDTO.class))).thenReturn(sampleTodo);

        TodoDTO result = todoService.updateTodoService(sampleTodo.getId(), sampleTodo);

        assertNotNull(result);
        assertEquals(sampleTodo.getId(), result.getId());
        verify(todoRepository, times(1)).updateTodo(sampleTodo.getId(), sampleTodo);
    }

    @Test
    void testDeleteTodoService() {
        when(todoRepository.deleteTodoRepository(sampleTodo.getId())).thenReturn(true);

        boolean result = todoService.deleteTodoService(sampleTodo.getId());

        assertTrue(result);
        verify(todoRepository, times(1)).deleteTodoRepository(sampleTodo.getId());
    }

    @Test
    void testChangeStatusService_MarkAsDone() {
        sampleTodo.setDone(false);
        when(todoRepository.getTodoByIdRepository(sampleTodo.getId())).thenReturn(Optional.of(sampleTodo));
        when(todoRepository.changeStatusRepository(eq(sampleTodo.getId()), any(LocalDateTime.class))).thenReturn(true);

        boolean result = todoService.changeStatusService(sampleTodo.getId());

        assertTrue(result);
        verify(todoRepository, times(1)).changeStatusRepository(eq(sampleTodo.getId()), any(LocalDateTime.class));
    }

    @Test
    void testChangeStatusService_MarkAsNotDone() {
        sampleTodo.setDone(true);
        when(todoRepository.getTodoByIdRepository(sampleTodo.getId())).thenReturn(Optional.of(sampleTodo));
        when(todoRepository.changeStatusRepository(eq(sampleTodo.getId()), isNull())).thenReturn(true);

        boolean result = todoService.changeStatusService(sampleTodo.getId());

        assertTrue(result);
        verify(todoRepository, times(1)).changeStatusRepository(eq(sampleTodo.getId()), isNull());
    }

    @Test
    void testGetMetricsService() {
        Map<String, String> mockMetrics = new HashMap<>();
        mockMetrics.put("general", "85");
        mockMetrics.put("low", "90");
        mockMetrics.put("medium", "80");
        mockMetrics.put("high", "70");

        when(todoRepository.getMetricsRepository()).thenReturn(mockMetrics);

        MetricsDTO result = todoService.getMetricsService();

        assertNotNull(result);
        assertEquals("85", result.getGeneralAverage());
        assertEquals("90", result.getLowAverage());
        assertEquals("80", result.getMediumAverage());
        assertEquals("70", result.getHighAverage());
        verify(todoRepository, times(1)).getMetricsRepository();
    }
    
}
