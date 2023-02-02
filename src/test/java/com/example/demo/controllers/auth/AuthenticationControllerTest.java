package com.example.demo.controllers.auth;

import com.example.demo.model.request.AuthenticationRequest;
import com.example.demo.model.request.RegisterRequest;
import com.example.demo.model.response.AuthenticationResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
class AuthenticationControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = {"/authController/delete_created_user.sql"})
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = {"/authController/delete_created_user.sql"})
    void givenRegisterRequest_whenRegister_thenReturnTokenAndResponseCodeOK() {
        //GIVEN

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstName("tester");
        registerRequest.setLastName("tester");
        registerRequest.setEmail("test@gmail.com");
        registerRequest.setPassword("12340987");

        JsonNode requestBodyJson = objectMapper.valueToTree(registerRequest);

        // When
        final HttpEntity<JsonNode> request = new HttpEntity<>(requestBodyJson, new HttpHeaders());

        ResponseEntity<AuthenticationResponse> response = restTemplate.exchange("/api/v1/auth/register",
                HttpMethod.POST, request,
                new ParameterizedTypeReference<>() {
                });

        // THEN
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(AuthenticationResponse.class, Objects.requireNonNull(response.getBody()).getClass());
        assertNotNull(response.getBody());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = {"/authController/delete_created_user.sql"})
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = {"/authController/delete_created_user.sql"})
    void givenRegisterRequestWithPasswordLengthLessThan8_whenRegister_thenReturnResponseCodeBadRequest() {
        //GIVEN

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstName("tester");
        registerRequest.setLastName("tester");
        registerRequest.setEmail("test@gmail.com");
        registerRequest.setPassword("1234098");

        JsonNode requestBodyJson = objectMapper.valueToTree(registerRequest);

        // When
        final HttpEntity<JsonNode> request = new HttpEntity<>(requestBodyJson, new HttpHeaders());

        ResponseEntity<AuthenticationResponse> response = restTemplate.exchange("/api/v1/auth/register",
                HttpMethod.POST, request,
                new ParameterizedTypeReference<>() {
                });

        // THEN
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = {"/authController/delete_created_user.sql"})
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = {"/authController/delete_created_user.sql"})
    void givenRegisterRequestWithFirstNameLengthLessThan2_whenRegister_thenReturnResponseCodeBadRequest() {
        //GIVEN

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstName("t");
        registerRequest.setLastName("tester");
        registerRequest.setEmail("test@gmail.com");
        registerRequest.setPassword("12340981");

        JsonNode requestBodyJson = objectMapper.valueToTree(registerRequest);

        // When
        final HttpEntity<JsonNode> request = new HttpEntity<>(requestBodyJson, new HttpHeaders());

        ResponseEntity<AuthenticationResponse> response = restTemplate.exchange("/api/v1/auth/register",
                HttpMethod.POST, request,
                new ParameterizedTypeReference<>() {
                });

        // THEN
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = {"/authController/delete_created_user.sql", "/authController/create_a_user.sql"})
    void givenCreatedUserDetails_whenAuthenticate_thenReturnTokenAndResponseCodeOK() {

        //GIVEN
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setEmail("test@gmail.com");
        authenticationRequest.setPassword("11112222");

        JsonNode requestBodyJson = objectMapper.valueToTree(authenticationRequest);

        // When
        final HttpEntity<JsonNode> request = new HttpEntity<>(requestBodyJson, new HttpHeaders());

        ResponseEntity<AuthenticationResponse> response = restTemplate.exchange("/api/v1/auth/authenticate",
                HttpMethod.POST, request,
                new ParameterizedTypeReference<>() {
                });

        // THEN
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(AuthenticationResponse.class, Objects.requireNonNull(response.getBody()).getClass());
        assertNotNull(response.getBody());
    }

}