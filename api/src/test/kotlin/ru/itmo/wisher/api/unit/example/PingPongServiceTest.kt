package ru.itmo.wisher.api.unit.example

import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import ru.itmo.wisher.api.example.application.PingPongRepository
import ru.itmo.wisher.api.example.application.PingPongService
import ru.itmo.wisher.api.example.domain.PingPong
import java.util.*

class PingPongServiceTest {
    private val pingPongRepository: PingPongRepository = mock()

    private val sut =
        PingPongService(
            pingPongRepository = pingPongRepository,
        )

    @BeforeEach
    fun setUp() =
        runBlocking<Unit> {
            whenever(pingPongRepository.save(any()))
                .thenReturn(PingPong(value = "ping"))
        }

    @Test
    fun `create ping pong with value`() {
        val actual = runBlocking { sut.create("ping") }

        assertThat(actual)
            .usingRecursiveComparison()
            .ignoringFieldsOfTypes(UUID::class.java)
            .isEqualTo(
                PingPong(
                    value = "ping",
                ),
            )
    }
}
