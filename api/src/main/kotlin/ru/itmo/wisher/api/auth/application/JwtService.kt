package ru.itmo.wisher.api.auth.application

import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.security.Key
import java.time.LocalDate
import java.util.Map

@Component
class JwtService {

    @Value("\${security.jwt.secret-key}")
    private val secretKey: String? = null

    @Value("\${security.jwt.expiration-time}")
    val expirationTime: Long = 0

    private fun key(): Key {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))
    }

    fun generateToken(authentication: Authentication): String {
        val username = authentication.name
        val now = LocalDate.now()
        val expirationDate = now.plusDays(expirationTime)

        return Jwts.builder()
            .setIssuedAt(java.sql.Date.valueOf(now))
            .setExpiration(java.sql.Date.valueOf(expirationDate))
            .addClaims(
                Map.ofEntries<String, Any>(
                    Map.entry("username", username),
                    /*Map.entry(
                        "roles",
                        authentication.authorities
                            .stream()
                            .map { role: GrantedAuthority? -> role.toString().substring(5) }
                            .toArray(),
                    ),*/
                ),
            )
            .signWith(key(), SignatureAlgorithm.HS256)
            .compact()
    }

    fun getUsername(token: String?): String {
        return Jwts.parserBuilder()
            .setSigningKey(key())
            .build()
            .parseClaimsJws(token)
            .body
            .get("username", String::class.java)
    }

    fun validateToken(token: String?): Boolean {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
            return true
        } catch (e: Exception) {
            return false
        }
    }
}
