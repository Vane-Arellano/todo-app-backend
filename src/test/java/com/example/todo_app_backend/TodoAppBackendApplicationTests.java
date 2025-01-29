package com.example.todo_app_backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.todo_app_backend.dtos.TodoDTO;
import com.example.todo_app_backend.repositories.TodoRepository;

@SpringBootTest
class TodoAppBackendApplicationTests {
	
	private TodoRepository todoRepository; 

	@BeforeEach
	void setUp() {
		todoRepository = new TodoRepository();
	}

	@Test
	void testCreateTodoRepository() {
		TodoDTO todo = new TodoDTO("Test Todo", "medium", Optional.of(LocalDateTime.now())); 
		TodoDTO createdTodo = todoRepository.createTodoRepository(todo); 

		assertNotNull(createdTodo);
		assertEquals("Test Todo", createdTodo.getName());
		assertEquals(1, todoRepository.getTodosRepository(0, 10).size());
	}

	@Test 
	void testGetTodosRepository() {
		TodoDTO todo1 = new TodoDTO("Test Todo 1", "medium", Optional.of(LocalDateTime.now())); 
		TodoDTO todo2 = new TodoDTO("Test Todo 2", "high", Optional.of(LocalDateTime.now())); 
		
		todoRepository.createTodoRepository(todo1);
		todoRepository.createTodoRepository(todo2);

		Map<String, Object> todos = todoRepository.getTodosRepository(0, 10); 

		assertEquals(2, todos.size());
		// assertTrue(todos.contains(todo1));
		// assertTrue(todos.contains(todo2));
	}

	@Test
	void testGetTodoByIdRepository(){
		TodoDTO todo = new TodoDTO("Test Todo", "medium", Optional.of(LocalDateTime.now())); 
		TodoDTO createdTodo = todoRepository.createTodoRepository(todo);
		System.out.println(createdTodo.getId());

		Optional<TodoDTO> foundTodo = todoRepository.getTodoByIdRepository(createdTodo.getId()); 
		assertTrue(foundTodo.isPresent());
		assertEquals("Test Todo", foundTodo.get().getName());
	}

	@Test
	void testUpdateTodo(){
		TodoDTO todo = new TodoDTO("Test Todo", "medium", Optional.of(LocalDateTime.now())); 
		TodoDTO createdTodo = todoRepository.createTodoRepository(todo);
		
		String todoId = createdTodo.getId(); 
		todo.setName("Updated name");
		todo.setPriority("low");
		LocalDateTime updatedDate = LocalDateTime.now().plusDays(1);
		todo.setDueDate(updatedDate);


		todoRepository.updateTodo(todoId, todo);
		Optional<TodoDTO> foundTodo = todoRepository.getTodoByIdRepository(todoId); 

		assertTrue(foundTodo.isPresent());
		assertEquals("Updated name", foundTodo.get().getName());
		assertEquals("low", foundTodo.get().getPriority());
		assertEquals(updatedDate, foundTodo.get().getDueDate());
	}

	@Test
	void testDeleteTodoRepository(){
		TodoDTO todo = new TodoDTO("Test Todo", "medium", Optional.of(LocalDateTime.now())); 
		TodoDTO createdTodo = todoRepository.createTodoRepository(todo);

		boolean isDeleted = todoRepository.deleteTodoRepository(createdTodo.getId()); 

		assertTrue(isDeleted);
		assertEquals(0, todoRepository.getTodosRepository(0,10).size());
	}

	@Test
	void testChangeStatusRepository(){
		TodoDTO todo = new TodoDTO("Test Todo", "medium", Optional.of(LocalDateTime.now())); 
		TodoDTO createdTodo = todoRepository.createTodoRepository(todo);
		String todoId = createdTodo.getId(); 

		boolean statusChanged = todoRepository.changeStatusRepository(todoId, LocalDateTime.now().plusMinutes(30));
		Optional <TodoDTO> updatedTodo = todoRepository.getTodoByIdRepository(todoId); 

		assertTrue(statusChanged);
		assertTrue(updatedTodo.isPresent());
		assertTrue(updatedTodo.get().isDone());
		assertNotNull(updatedTodo.get().getDoneDate());
	}

	@Test
	void testGetMetricsRepository() {
		Optional<LocalDateTime> now = Optional.of(LocalDateTime.now());
		TodoDTO todo1 = new TodoDTO("Task test minutes", "low", now);
		TodoDTO todo2 = new TodoDTO("Task test hours", "medium", now);
		TodoDTO todo3 = new TodoDTO("Task test days", "high", now);

		todo1.setDone(true);
		todo1.setDoneDate(todo1.getDoneDate().plusMinutes(30));
		todo2.setDone(true);
		todo2.setDoneDate(todo2.getDoneDate().plusHours(2));
		todo3.setDone(true);
		todo3.setDoneDate(todo3.getDoneDate().plusDays(1));

		todoRepository.createTodoRepository(todo1);
		todoRepository.createTodoRepository(todo2);
		todoRepository.createTodoRepository(todo3);

		Map<String, String> metrics = todoRepository.getMetricsRepository();

		assertNotNull(metrics);
		assertTrue(metrics.containsKey("general"));
		assertTrue(metrics.containsKey("low"));
		assertTrue(metrics.containsKey("medium"));
		assertTrue(metrics.containsKey("high"));
		assertEquals("00:30 minutes", metrics.get("low")); 
		assertEquals("02:00 hours", metrics.get("medium"));
		assertEquals("1 00:00 days", metrics.get("high"));
	}
}
