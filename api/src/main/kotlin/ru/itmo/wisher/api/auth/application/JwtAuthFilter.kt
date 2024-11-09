package ru.itmo.wisher.api.auth.application

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthFilter(
    private val jwtService: JwtService,
    private val userDetailsService: UserDetailsService,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val token: String? = getTokenFromRequest(request)

        println("TOKEN: $token")

        if (StringUtils.hasText(token) && jwtService.validateToken(token)) {
            val username: String = jwtService.getUsername(token)
            println("username: $username")
            val userDetails = userDetailsService.loadUserByUsername(username)

            println("userDeta: $userDetails")
            val authToken =
                UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.authorities,
                )

            authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = authToken
        }

        filterChain.doFilter(request, response)
    }

    private fun getTokenFromRequest(request: HttpServletRequest): String? {
        val authHeader = request.getHeader("Authorization")

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer "))
            return authHeader.substring(
                7,
            )

        return null
    }
}
