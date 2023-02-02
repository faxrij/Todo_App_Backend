package com.example.demo.controllers;

import com.example.demo.helper.TokenHelper;
import com.example.demo.model.entity.Todo;
import com.example.demo.model.request.TodoForm;
import com.example.demo.model.request.UpdateTodoRequest;
import com.example.demo.model.response.TodoDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
class TodoControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = {"/todoController/add_todo.sql"})
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = {"/todoController/delete_todo.sql"})
    public void givenUserToken_whenGetTodos_thenReturnListOfTodoResponsesAndResponseCodeIs200() {
        //GIVEN
        String userToken = TokenHelper.USER_TOKEN_WITH_ID_2;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + userToken);

        // When
        final HttpEntity<String> request = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<List<TodoDto>> response = restTemplate.exchange("/api/v1/todo",
                HttpMethod.GET, request,
                new ParameterizedTypeReference<>() {
                });
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(5, Objects.requireNonNull(response.getBody()).size());
        assertEquals("Todo2", response.getBody().get(1).getName());
        assertEquals("Todo3", response.getBody().get(2).getName());
        assertEquals("Todo Desc 5", response.getBody().get(4).getDescription());
        assertTrue(response.getBody().get(3).isDone());

    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = {"/todoController/add_todo.sql"})
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = {"/todoController/delete_todo.sql"})
    public void givenUserToken_whenGetTodosWithParam_thenReturnListOfTodoResponsesAndResponseCodeIs200() {
        //GIVEN
        String userToken = TokenHelper.USER_TOKEN_WITH_ID_2;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + userToken);

        // When
        final HttpEntity<String> request = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<Todo> response = restTemplate.exchange("/api/v1/todo/3",
                HttpMethod.GET, request,
                new ParameterizedTypeReference<>() {
                });
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Todo3", Objects.requireNonNull(Objects.requireNonNull(response.getBody()).getName()));
        assertEquals("Todo Desc 3", Objects.requireNonNull(response.getBody().getDescription()));
        assertFalse(response.getBody().isDone());
    }

    @Test
    void notGivenUserToken_whenGetTodos_thenReturnResponseCodeIsUnAuthorized() {
        //GIVEN
        HttpHeaders httpHeaders = new HttpHeaders();

        // When
        final HttpEntity<String> request = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<Todo> response = restTemplate.exchange("/api/v1/todo/3",
                HttpMethod.GET, request,
                new ParameterizedTypeReference<>() {
                });
        // Then
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = {"/todoController/delete_todo.sql"})
    void givenUserToken_whenAddTodo_thenReturnResponseCodeIs201Created() {
        //GIVEN
        String userToken = TokenHelper.USER_TOKEN_WITH_ID_2;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + userToken);

        TodoForm todoForm = new TodoForm();
        todoForm.setName("todo test");
        todoForm.setDescription("todo test desc");
        todoForm.setDone(false);
        todoForm.setDuration(9);
        JsonNode requestBodyJson = objectMapper.valueToTree(todoForm);

        // When
        final HttpEntity<JsonNode> request = new HttpEntity<>(requestBodyJson, httpHeaders);
        ResponseEntity<Todo> response = restTemplate.exchange("/api/v1/todo",
                HttpMethod.POST, request,
                new ParameterizedTypeReference<>() {
                });
        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = {"/todoController/add_todo.sql"})
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = {"/todoController/delete_todo.sql"})
    void givenUserToken_whenUpdateTodo_thenReturnResponseCodeIs200() {
        // GIVEN
        String userToken = TokenHelper.USER_TOKEN_WITH_ID_2;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + userToken);

        UpdateTodoRequest updateTodoRequest = new UpdateTodoRequest();
        updateTodoRequest.setName("Todo update");
        updateTodoRequest.setDescription("TODO DESCR");
        updateTodoRequest.setDone(true);
        updateTodoRequest.setDuration(4);
        JsonNode requestBodyJson = objectMapper.valueToTree(updateTodoRequest);

        // When
        final HttpEntity<JsonNode> request = new HttpEntity<>(requestBodyJson, headers);
        final ResponseEntity<String> responsePut = restTemplate.exchange("/api/v1/todo/1", HttpMethod.PUT, request,
                new ParameterizedTypeReference<>() {
                });
        final ResponseEntity<Todo> responseGet = restTemplate.exchange("/api/v1/todo/1", HttpMethod.GET, request,
                new ParameterizedTypeReference<>() {
                });

        // Then
        assertEquals(HttpStatus.OK, responsePut.getStatusCode());
        assertEquals(HttpStatus.OK, responseGet.getStatusCode());

    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = {"/todoController/add_todo.sql", "/todoController/add_one_user.sql"})
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = {"/todoController/delete_todo.sql"})
    void givenUserToken_whenDeleteTodo_thenReturnResponseCodeIs200() {
        //GIVEN
        String userToken = TokenHelper.USER_TOKEN_WITH_ID_2;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + userToken);

        // When
        final HttpEntity<String> request = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange("/api/v1/todo/3",
                HttpMethod.DELETE, request,
                new ParameterizedTypeReference<>() {
                });
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}