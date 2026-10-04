package com.example.bandapi.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.util.Date
import javax.crypto.SecretKey

@Service
class JwtService(

    @Value("\${jwt.secret}")
    private val secret: String,

    @Value("\${jwt.expiration}")
    private val expiration: Long
) {

    private val signingKey: SecretKey
        get() = Keys.hmacShaKeyFor(
            secret.toByteArray(StandardCharsets.UTF_8)
        )

    fun generateToken(email: String): String {

        val now = Date()

        val expirationDate = Date(
            now.time + expiration
        )

        return Jwts.builder()
            .subject(email)
            .issuedAt(now)
            .expiration(expirationDate)
            .signWith(signingKey)
            .compact()
    }

    fun extractEmail(token: String): String {

        return Jwts.parser()
            .verifyWith(signingKey)
            .build()
            .parseSignedClaims(token)
            .payload
            .subject
    }

    fun isTokenValid(
        token: String,
        email: String
    ): Boolean {

        return try {

            val extractedEmail = extractEmail(token)

            extractedEmail == email

        } catch (exception: Exception) {

            false
        }
    }
}