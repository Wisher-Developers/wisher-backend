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
import ru.itmo.wisher.api.user.application.UserService
import ru.itmo.wisher.api.user.domain.User

@Component
class JwtAuthFilter(
    private val jwtService: JwtService,
    private val userService: UserService,
    private val userDetailsService: UserDetailsService,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val token: String? = getTokenFromRequest(request)

        if (jwtService.validateToken(token)) {
            val username: String = jwtService.getUsername(token)
            val userDetails = userDetailsService.loadUserByUsername(username)

            val user = (userDetails as User).loggedIn()

            userService.save(user)

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
            return authHeader.substring(7)

        return null
    }
}
