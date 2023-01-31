package com.example.demo.controllers;

import com.example.demo.config.JwtAuthenticationFilter;
import com.example.demo.config.SecurityConfiguration;
import com.example.demo.exception.BusinessException;
import com.example.demo.model.converter.TodoConverter;
import com.example.demo.model.entity.Todo;
import com.example.demo.repository.TodoRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.TodoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.BeanNotOfRequiredTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.lang.module.Configuration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TodoController.class)
class TodoControllerTest {



    @MockBean
    private JwtAuthenticationFilter jwtAuthFilter;
    @MockBean private AuthenticationProvider authenticationProvider;
    @Autowired private TodoConverter converter;
    @MockBean
    private SecurityConfiguration webSecurityConfiguration;
    @Mock private TodoRepository repository;
    @Autowired
    private MockMvc mockMvc;
    @Test()
    void shouldNotGetAllTodos(){
        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/todo");
        assertThatThrownBy(() -> mockMvc.perform(request))
                .isInstanceOf(BeanNotOfRequiredTypeException.class);
    }
    @WithMockUser(username="iygoc@gmail.com", password="12343535533")
    @Test()
    void shouldGetAllTodos() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/todo");
        mockMvc.perform(request).andExpect(status().isOk());
    }


    @Test
    void getTodo() {
    }

    @Test
    void addTodo() {
    }

    @Test
    void updateTodo() {
    }

    @Test
    void deleteTodo() {
    }
}