package com.emrhnsyts.todo.controller;

import com.emrhnsyts.todo.entity.Todo;
import com.emrhnsyts.todo.request.TodoRequest;
import com.emrhnsyts.todo.request.TodoUpdateRequest;
import com.emrhnsyts.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @GetMapping
    public List<Todo> getTodos() {
        return todoService.getTodos();
    }

    @GetMapping("/{todoId}")
    public Todo getTodo(@PathVariable("todoId") Long todoId) {
        return todoService.getTodo(todoId);
    }

    @DeleteMapping("/{todoId}")
    public void deleteTodo(@PathVariable("todoId") Long todoId) {
        todoService.deleteTodo(todoId);
    }

    @PostMapping
    public Todo addTodo(@Valid @RequestBody TodoRequest todoRequest) {
        return todoService.addTodo(todoRequest);
    }

    @PutMapping("/{todoId}")
    public Todo updateTodo(@Valid @RequestBody TodoUpdateRequest todoUpdateRequest, @PathVariable("todoId") Long todoId) {
        return todoService.updateTodo(todoUpdateRequest, todoId);
    }

    @GetMapping("/users/{userId}")
    public List<Todo> getTodosByAppUserId(@PathVariable("userId") Long appUserId) {
        return todoService.getByAppUserId(appUserId);
    }
}
