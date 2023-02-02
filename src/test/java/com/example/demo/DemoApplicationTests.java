package com.example.demo;


import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class DemoApplicationTests {
    public static PostgreSQLContainer postgreSQLContainer = (PostgreSQLContainer) (new PostgreSQLContainer("postgres:14")
            .withDatabaseName("db")
            .withUsername("root")
            .withPassword("rootTest"))
            .withReuse(true);

    @Test
    void contextLoads() {
    }

}
