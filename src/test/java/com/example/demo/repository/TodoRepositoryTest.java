package com.example.demo.repository;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@DataJpaTest
class TodoRepositoryTest {

    @Mock
    private TodoRepository underTest;

    @Test
    void findTodoByName() {
        String name = "todoName";
        underTest.findTodoByName(name);
        verify(underTest).findTodoByName(name);
    }

    @Test
    void findTodoById() {
        Long id = 1L;
        underTest.findTodoById(id);
        verify(underTest).findTodoById(id);
    }
}