package com.example.demo.services;

import com.example.demo.exception.BusinessException;
import com.example.demo.model.entity.Todo;
import com.example.demo.model.request.UpdateTodoRequest;
import com.example.demo.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;
    private TodoService underTest;
    @Mock Todo mainTodo;

    @BeforeEach
    void setUp() {
        underTest = new TodoService(todoRepository);
    }

    @Test
    void canGetAllTodos() {
        // when
        underTest.getAllTodos();
        //then
        verify(todoRepository).findAll();
    }

    @Test
    void canGetTodo() {
        //given
        Long todoId = 1L;

        when(todoRepository.existsById(todoId)).thenReturn(true);
        when(todoRepository.findById(todoId)).thenReturn(Optional.of(new Todo(
                1L,
                "Todo For Test",
                "Todo For Testing",
                true,
                12
        )));

        //when
        underTest.getTodo(todoId);
        //then
        verify(todoRepository, atLeast(2)).findById(todoId);
    }

    @Test
    void shouldNotGetTodoWhenTodoDoesNotExist() {
        //given
        Long todoId = 1L;

        when(todoRepository.existsById(todoId)).thenReturn(false);

        assertThatThrownBy(() -> underTest.getTodo(todoId))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Todo id with 1 does not exist");

    }

    @Test
    void shouldNotGetTodoWhenTodoExistsButItIsEmpty() {
        //given
        Long todoId = 1L;

        when(todoRepository.existsById(todoId)).thenReturn(true);
        when(todoRepository.findById(todoId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.getTodo(todoId))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Todo id with 1 does not exist");
    }

    @Test
    void canAddTodo() {
        //given
        Todo todo = new Todo(
                1L,
                "Todo For Test",
                "Todo For Testing",
                true,
                12
        );
        //when
        underTest.addTodo(todo);

        //then
        ArgumentCaptor<Todo> todoArgumentCaptor = ArgumentCaptor.forClass(Todo.class);

        verify(todoRepository).save(todoArgumentCaptor.capture());

        Todo capturedStudent = todoArgumentCaptor.getValue();

        assertThat(capturedStudent).isEqualTo(todo);
    }

    @Test
    void cannotAddTodo(){
        //given
        Todo todo = new Todo(
                1L,
                "Todo For Test",
                "Todo For Testing",
                true,
                12
        );
        given(todoRepository
                .findTodoByName(todo.getName()))
                .willReturn(Optional.of(todo));

        assertThatThrownBy(() -> underTest.addTodo(todo))
                .isInstanceOf(BusinessException.class)
                        .hasMessageContaining("todo name exists");

        verify(todoRepository, never()).save(any());
    }

    @Test
    void shouldNotUpdateTodo() {
        UpdateTodoRequest updateTodoRequest = new UpdateTodoRequest();
        Long todoId = 1L;
        when(todoRepository.findTodoById(todoId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.updateTodo(todoId, updateTodoRequest))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("todo with id 1 does not exist");
    }
    @Test
    void shouldUpdateTodo() {
        UpdateTodoRequest updateTodoRequest = new UpdateTodoRequest();
        Todo todo = new Todo();
        Long todoId = 1L;
        updateTodoRequest.setName("saf");
        updateTodoRequest.setDescription("saf");
        updateTodoRequest.setDone(true);
        updateTodoRequest.setDuration(12);

        when(todoRepository.findTodoById(todoId)).thenReturn(Optional.of(mainTodo));

        underTest.updateTodo(todoId,updateTodoRequest);

        verify(mainTodo).setName(updateTodoRequest.getName());
        verify(mainTodo).setDescription(updateTodoRequest.getDescription());
        verify(mainTodo).setDone(updateTodoRequest.isDone());
        verify(mainTodo).setDuration(updateTodoRequest.getDuration());

    }
    @Test
    void shouldDeleteTodo() {
        Long todoId = 1L;
        when(todoRepository.existsById(todoId)).thenReturn(true);
        underTest.deleteTodo(todoId);
        verify(todoRepository).deleteById(todoId);
    }

    @Test
    void shouldNotDeleteTodoWhenDoesNotExist() {
        Long todoId = 1L;
        when(todoRepository.existsById(todoId)).thenReturn(false);
        assertThatThrownBy(() -> underTest.deleteTodo(todoId))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Todo id with 1 does not exist");
    }
}