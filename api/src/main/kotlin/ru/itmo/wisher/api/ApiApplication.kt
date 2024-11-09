package ru.itmo.wisher.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@SpringBootApplication
@EnableWebSecurity
class ApiApplication

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}

@Configuration
class AppConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedMethods("*")
            .allowCredentials(true)
            .allowedOriginPatterns("*")
    }
}
