package com.example.bandapi.controller

import com.example.bandapi.dto.user.UserCreateRequest
import com.example.bandapi.dto.user.UserResponse
import com.example.bandapi.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @Valid @RequestBody request: UserCreateRequest
    ): UserResponse {
        return userService.create(request)
    }

    @GetMapping
    fun findAll(): List<UserResponse> {
        return userService.findAll()
    }

    @GetMapping("/{id}")
    fun findById(
        @PathVariable id: Long
    ): UserResponse {
        return userService.findById(id)
    }
}