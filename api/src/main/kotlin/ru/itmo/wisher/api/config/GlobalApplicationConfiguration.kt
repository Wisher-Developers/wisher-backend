package ru.itmo.wisher.api.config

import jakarta.servlet.*
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.io.IOException

@Configuration
class GlobalApplicationConfiguration {
    @Bean
    fun corsConfigurer() = object : WebMvcConfigurer {
        override fun addCorsMappings(registry: CorsRegistry) {
            registry
                .addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true)
        }
    }

    @Bean
    fun corsFilter(): Filter {
        return object : Filter {
            override fun init(filterConfig: FilterConfig?) {}

            override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
                val httpServletResponse = response as HttpServletResponse
                httpServletResponse.setHeader("Access-Control-Allow-Origin", "*")
                httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                httpServletResponse.setHeader("Access-Control-Allow-Headers", "*")
                httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true")
                chain.doFilter(request, response)
            }

            override fun destroy() {}
        }
    }
}
