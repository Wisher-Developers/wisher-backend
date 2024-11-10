package ru.itmo.wisher.api.auth.application

import kotlinx.coroutines.runBlocking
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import ru.itmo.wisher.api.user.application.UserRepository
import ru.itmo.wisher.api.user.domain.exception.NoSuchUsername

@Configuration
@EnableWebSecurity(debug = true)
class SecurityConfiguration : WebMvcConfigurer {

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
            ?: throw NoSuchUsername(username)
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
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers("/api/auth/**").permitAll()
                it.requestMatchers("/api/users/{id}").permitAll()
                it.requestMatchers("/api/item/{id}").permitAll()
                it.requestMatchers("/api/wishlist/{id}").permitAll()
                it.requestMatchers("/api/wishlist/user/{id}").permitAll()
                it.anyRequest().authenticated()
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authenticationProvider(authenticationProvider)
            .anonymous { it.disable() }
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter::class.java,
            )

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOriginPatterns = listOf("/**")
        configuration.allowedMethods = listOf("*")
        configuration.allowedHeaders = listOf("*")
        configuration.allowCredentials = true
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}
