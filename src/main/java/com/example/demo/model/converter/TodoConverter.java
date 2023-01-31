package com.example.demo.model.converter;

import com.example.demo.model.entity.Todo;
import com.example.demo.model.request.TodoForm;
import com.example.demo.model.response.TodoDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TodoConverter {

    public TodoDto entityToDto(Todo todo) {
        TodoDto todoDto = new TodoDto();
        todoDto.setName(todo.getName());
        todoDto.setDescription(todo.getDescription());
        todoDto.setDone(todo.isDone());
        System.out.println();
        return todoDto;
    }

    public List<TodoDto> entityToDto(List<Todo> todos) {
        return todos.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public Todo dtoToEntity(TodoDto todoDto) {
        Todo todo = new Todo();
        todo.setName(todoDto.getName());
        todo.setDescription(todoDto.getDescription());
        todo.setDone(todoDto.isDone());

        return todo;
    }

    public Todo formToEntity(TodoForm todoForm) {
        Todo todo = new Todo();
        todo.setName(todoForm.getName());
        todo.setDescription(todoForm.getDescription());
        todo.setDone(todoForm.isDone());
        todo.setDuration(todoForm.getDuration());

        return todo;
    }

    public List<Todo> dtoToEntity(List<TodoDto> dto) {
        return dto.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }
}
