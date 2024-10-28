package ru.itmo.wisher.api.integration

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import ru.itmo.wisher.api.utils.IntegrationTestConfiguration

class ExampleIntegrationTest : IntegrationTestConfiguration() {

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    @Test
    fun `test database connection`() {
        val result = jdbcTemplate.queryForObject("SELECT 1", Int::class.java)
        assert(result == 1)
    }
}
