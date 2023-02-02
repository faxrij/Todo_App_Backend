package com.example.demo.controllers;

import com.example.demo.model.entity.Todo;
import com.example.demo.model.converter.TodoConverter;
import com.example.demo.model.request.TodoForm;
import com.example.demo.model.request.UpdateTodoRequest;
import com.example.demo.model.response.TodoDto;
import com.example.demo.services.TodoService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/todo")
public class TodoController {

    private final TodoService todoService;

    private final TodoConverter todoConverter;


    public TodoController(TodoService todoService, TodoConverter todoConverter) {
        this.todoService = todoService;
        this.todoConverter = todoConverter;
    }

    @GetMapping
    public List<TodoDto> getAllTodos() {
        return todoConverter.entityToDto(this.todoService.getAllTodos());
    }

    @GetMapping(path = "{todoId}")
    public Todo getTodo(@PathVariable("todoId") Long id) {
        return this.todoService.getTodo(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addTodo(@RequestBody @NotNull TodoForm todoForm){
        Todo todo = todoConverter.formToEntity(todoForm);
        this.todoService.addTodo(todo);
    }

    @PutMapping(path = "{todoId}")
    public void updateTodo(
            @PathVariable("todoId") Long todoId,
            @RequestBody @Valid UpdateTodoRequest updateTodoRequest) {
        todoService.updateTodo(todoId, updateTodoRequest);
    }


    @DeleteMapping(path = "{todoId}")
    public void deleteTodo(@PathVariable("todoId") Long id){
        todoService.deleteTodo(id);
    }
}