package com.example.demo.services;

import com.example.demo.exception.BusinessException;
import com.example.demo.model.request.UpdateTodoRequest;
import com.example.demo.repository.TodoRepository;
import com.example.demo.model.entity.Todo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getAllTodos(){
        return this.todoRepository.findAll();
    }

    public Todo getTodo(Long todoId) {
        boolean exists = todoRepository.existsById(todoId);
        if (!exists) {
            throw new BusinessException("Todo id with " + todoId + " does not exist");
        }
        if (todoRepository.findById(todoId).isPresent()) {
            return todoRepository.findById(todoId).get();
        }
        throw new BusinessException("Todo id with " + todoId + " does not exist");
    }

    public void addTodo(Todo todo) {
        Optional<Todo> todoOptional = this.todoRepository
                .findTodoByName(todo.getName());

        if (todoOptional.isPresent()) {
            throw new BusinessException("todo name exists");
        }

        this.todoRepository.save(todo);
    }


    public void updateTodo(Long todoId,
                           UpdateTodoRequest updateTodoRequest) {
        Todo todo = todoRepository.findTodoById(todoId).orElseThrow(() ->
                new BusinessException("todo with id " + todoId + " does not exist"));


        if (!Objects.equals(todo.getName(), updateTodoRequest.getName())) {
            todo.setName(updateTodoRequest.getName());
        }
        if (!Objects.equals(todo.getDescription(), updateTodoRequest.getDescription())) {
            todo.setDescription(updateTodoRequest.getDescription());
        }
        if (!Objects.equals(todo.isDone(), updateTodoRequest.isDone())) {
            todo.setDone(updateTodoRequest.isDone());
        }
        if (!Objects.equals(todo.getDuration(), updateTodoRequest.getDuration())) {
            todo.setDuration(updateTodoRequest.getDuration());
        }

    }
    public void deleteTodo(Long todoId){
        boolean exists = todoRepository.existsById(todoId);
        if (!exists) {
            throw new BusinessException("Todo id with " + todoId + " does not exist");
        }
        todoRepository.deleteById(todoId);
    }
}