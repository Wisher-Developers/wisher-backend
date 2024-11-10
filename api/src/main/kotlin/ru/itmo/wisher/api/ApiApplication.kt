package ru.itmo.wisher.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

@SpringBootApplication
@EnableWebSecurity
class ApiApplication

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}
