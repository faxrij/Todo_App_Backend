package com.example.demo;


import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
public class DemoApplicationTests {
    public static PostgreSQLContainer postgreSQLContainer = (PostgreSQLContainer) (new PostgreSQLContainer("postgres:14")
            .withDatabaseName("db")
            .withUsername("root")
            .withPassword("rootTest"))
            .withReuse(true);

    //	@Test
//	void test() {
//		assertThat(postgreSQLContainer.isRunning()).isTrue();
//	}
    @Test
    void contextLoads() {
    }

}
