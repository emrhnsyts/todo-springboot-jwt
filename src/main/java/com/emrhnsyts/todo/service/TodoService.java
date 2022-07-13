package com.emrhnsyts.todo.service;

import com.emrhnsyts.todo.entity.AppUser;
import com.emrhnsyts.todo.entity.Todo;
import com.emrhnsyts.todo.exception.TodoNotFoundException;
import com.emrhnsyts.todo.repository.TodoRepository;
import com.emrhnsyts.todo.request.TodoRequest;
import com.emrhnsyts.todo.request.TodoUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final AppUserService appUserService;

    public List<Todo> getTodos() {
        return todoRepository.findAll();
    }

    public Todo getTodo(Long todoId) {
        Optional<Todo> optionalTodo = todoRepository.findById(todoId);
        if (optionalTodo.isPresent()) {
            return optionalTodo.get();
        } else {
            throw new TodoNotFoundException("Todo not found.");
        }
    }

    public Todo addTodo(TodoRequest todoRequest) {
        return todoRepository.save(Todo.builder()
                .text(todoRequest.getText())
                .isCrossed(false)
                .appUser(appUserService.getAppUser(todoRequest.getAppUserId()))
                .build());
    }

    public void deleteTodo(Long todoId) {
        Optional<Todo> optionalTodo = todoRepository.findById(todoId);
        if (optionalTodo.isPresent()) {
            todoRepository.deleteById(optionalTodo.get().getId());
        } else {
            throw new TodoNotFoundException("Todo not found.");
        }
    }

    public Todo updateTodo(TodoUpdateRequest todoUpdateRequest, Long todoId) {
        Optional<Todo> optionalTodo = todoRepository.findById(todoId);
        if (optionalTodo.isPresent()) {
            Todo todo = optionalTodo.get();
            todo.setText(todoUpdateRequest.getText());
            todo.setIsCrossed(todoUpdateRequest.getIsCrossed());
            return todoRepository.save(todo);
        } else {
            throw new TodoNotFoundException("Todo not found.");
        }
    }

    public List<Todo> getByAppUserId(Long appUserId) {
        AppUser appUser = appUserService.getAppUser(appUserId);
        return todoRepository.findByAppUserId(appUser.getId());
    }
}
