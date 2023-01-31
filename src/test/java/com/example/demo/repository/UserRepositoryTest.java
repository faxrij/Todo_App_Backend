package com.example.demo.repository;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.mockito.Mockito.verify;

@DataJpaTest
class UserRepositoryTest {
    @Mock
    private UserRepository underTest;
    @Test
    void findByEmail() {
        String email = "user@gmail.com";
        underTest.findByEmail(email);
        verify(underTest).findByEmail(email);
    }
}