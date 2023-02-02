package com.example.demo.controllers;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
class DefaultPageControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void returnOK() {
        // When
        final HttpEntity<String> request = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity<String> response = restTemplate.exchange("/",
                HttpMethod.GET, request,
                new ParameterizedTypeReference<>() {
                });
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}