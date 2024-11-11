package ru.itmo.wisher.api.user.domain

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

data class User(
    val id: UUID,
    val userName: String,
    val email: String,
    val avatarLink: String? = null,
    private val password: String,
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf()

    override fun getPassword() = password

    override fun getUsername() = userName

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true

    companion object {
        fun current(): User = currentOrNull() ?: throw IllegalStateException()

        fun currentOrNull(): User? =
            try {
                SecurityContextHolder.getContext().authentication.principal as User
            } catch (ex: Exception) {
                null
            }
    }
}
