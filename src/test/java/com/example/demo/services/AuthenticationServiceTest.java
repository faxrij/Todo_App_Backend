package com.example.demo.services;

import com.example.demo.config.JwtService;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.entity.User;
import com.example.demo.model.request.AuthenticationRequest;
import com.example.demo.model.request.RegisterRequest;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    private AuthenticationService underTest;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() {
        underTest = new AuthenticationService(userRepository, passwordEncoder, jwtService, authenticationManager);
    }

    @Test
    void register() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@gmail.com");
        request.setPassword("12344551224");
        User user = new User();
        user.setEmail("test@gmail.com");
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        assertThatThrownBy(() -> underTest.register(request))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessageContaining("User Already Exists");
    }

    @Test
    void authenticate() {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("test@gmail.com");
        request.setPassword("12344551224");

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> underTest.authenticate(request))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User does not exist");
    }
}
