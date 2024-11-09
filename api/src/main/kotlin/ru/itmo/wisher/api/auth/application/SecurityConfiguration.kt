package ru.itmo.wisher.api.auth.application

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import ru.itmo.wisher.api.auth.domain.exception.UserException

@Configuration
@EnableMethodSecurity
@EnableWebSecurity(debug = true)
class SecurityConfiguration {

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager {
        return config.authenticationManager
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun userDetailsService(
        userRepository: UserRepository,
    ) = UserDetailsService { username ->
        runBlocking {
            userRepository.findByUsername(username)
        }
            ?: throw UserException(username)
    }

    @Bean
    fun authenticationProvider(
        userDetailsService: UserDetailsService,
        passwordEncoder: BCryptPasswordEncoder,
    ) = DaoAuthenticationProvider().apply {
        setUserDetailsService(userDetailsService)
        setPasswordEncoder(passwordEncoder)
    }

    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        jwtAuthenticationFilter: JwtAuthFilter,
        authenticationProvider: AuthenticationProvider,
    ): SecurityFilterChain {
        http
            .csrf {
                it.disable()
            }
            .authorizeHttpRequests {
                it.requestMatchers("/api/auth/signup").permitAll()
                it.requestMatchers("/api/auth/login").permitAll()
                it.anyRequest().authenticated()
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter::class.java,
            )

        return http.build()
    }

    @Bean
    fun authenticationEntryPoint() =
        object : AuthenticationEntryPoint {

            private val logger: Logger = LoggerFactory.getLogger(this::class.java)

            override fun commence(
                request: HttpServletRequest?,
                response: HttpServletResponse?,
                authException: AuthenticationException?,
            ) {
                TODO("Not yet implemented")
            }
        }
}
