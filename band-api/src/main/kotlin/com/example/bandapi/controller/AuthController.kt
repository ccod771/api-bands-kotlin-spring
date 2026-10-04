package com.example.bandapi.controller

import com.example.bandapi.dto.auth.LoginRequest
import com.example.bandapi.dto.auth.LoginResponse
import com.example.bandapi.service.AuthService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/login")
    fun login(
        @Valid @RequestBody request: LoginRequest
    ): LoginResponse {

        return authService.login(request)
    }
}