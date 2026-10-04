package com.example.bandapi.service

import com.example.bandapi.dto.auth.LoginRequest
import com.example.bandapi.dto.auth.LoginResponse
import com.example.bandapi.security.JwtService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService
) {

    fun login(request: LoginRequest): LoginResponse {

        val authentication = UsernamePasswordAuthenticationToken(
            request.email,
            request.password
        )

        authenticationManager.authenticate(authentication)

        val token = jwtService.generateToken(
            request.email
        )

        return LoginResponse(token)
    }
}