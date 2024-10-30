package ru.itmo.wisher.api.utils

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@IntegrationTest
abstract class IntegrationTestConfiguration {
    companion object {
        private val postgres: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:16")

        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            System.setProperty("DOCKER_HOST", "unix:///var/run/docker.sock")
            postgres.start()
        }

        @AfterAll
        @JvmStatic
        fun afterAll() {
            postgres.stop()
        }

        @DynamicPropertySource
        fun configureProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url") { postgres.jdbcUrl }
            registry.add("spring.datasource.username") { postgres.username }
            registry.add("spring.datasource.password") { postgres.password }
        }
    }
}
