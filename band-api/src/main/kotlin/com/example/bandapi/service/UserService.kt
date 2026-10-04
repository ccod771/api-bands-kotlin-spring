package com.example.bandapi.service

import com.example.bandapi.dto.user.UserCreateRequest
import com.example.bandapi.dto.user.UserResponse
import com.example.bandapi.model.User
import com.example.bandapi.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun create(request: UserCreateRequest): UserResponse {

        if (userRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("Email already registered")
        }

        if (userRepository.existsByUsername(request.username)) {
            throw IllegalArgumentException("Username already registered")
        }

        val user = User(
            username = request.username,
            email = request.email,
            password = request.password
        )

        val savedUser = userRepository.save(user)

        return UserResponse.from(savedUser)
    }

    fun findAll(): List<UserResponse> {
        return userRepository.findAll()
            .map(UserResponse::from)
    }

    fun findById(id: Long): UserResponse {
        val user = userRepository.findById(id)
            .orElseThrow {
                NoSuchElementException("User not found")
            }

        return UserResponse.from(user)
    }
}