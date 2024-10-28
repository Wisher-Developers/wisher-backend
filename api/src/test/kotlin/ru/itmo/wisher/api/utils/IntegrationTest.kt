package ru.itmo.wisher.api.utils

import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@TestPropertySource(locations = ["classpath:application-test.yml"])
@Tag("integration")
annotation class IntegrationTest
