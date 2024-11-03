package ru.itmo.wisher.api.config

import jakarta.servlet.*
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.io.IOException

@Configuration
class GlobalApplicationConfiguration {
    @Bean
    fun corsConfigurer() =
        object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry
                    .addMapping("/**")
                    .allowedOriginPatterns("*")
                    .allowedMethods("*")
                    .allowedHeaders("*")
            }
        }
}

@WebFilter("*")
class AddResponseHeaderFilter : Filter {
    @Throws(IOException::class, ServletException::class)
    override fun doFilter(
        request: ServletRequest?,
        response: ServletResponse,
        chain: FilterChain,
    ) {
        val httpServletResponse = response as HttpServletResponse
        httpServletResponse.setHeader(
            "Access-Control-Allow-Origin",
            "*",
        )
        chain.doFilter(request, response)
    }

    @Throws(ServletException::class)
    override fun init(filterConfig: FilterConfig?) {
        // ...
    }

    override fun destroy() {
        // ...
    }
}
