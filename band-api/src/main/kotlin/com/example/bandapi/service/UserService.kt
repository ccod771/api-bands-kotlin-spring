package com.example.bandapi.service

import com.example.bandapi.dto.user.UserCreateRequest
import com.example.bandapi.dto.user.UserResponse
import com.example.bandapi.dto.user.UserUpdateRequest
import com.example.bandapi.model.User
import com.example.bandapi.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
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
            password = passwordEncoder.encode(request.password)
        )

        return UserResponse.from(
            userRepository.save(user)
        )
    }

    fun findAll(): List<UserResponse> {
        return userRepository.findAll()
            .map(UserResponse::from)
    }

    fun findById(id: Long): UserResponse {
        return UserResponse.from(findUser(id))
    }

    fun update(
        id: Long,
        request: UserUpdateRequest
    ): UserResponse {

        val user = findUser(id)

        if (
            request.email != user.email &&
            userRepository.existsByEmail(request.email)
        ) {
            throw IllegalArgumentException("Email already registered")
        }

        if (
            request.username != user.username &&
            userRepository.existsByUsername(request.username)
        ) {
            throw IllegalArgumentException("Username already registered")
        }

        user.username = request.username
        user.email = request.email

        return UserResponse.from(
            userRepository.save(user)
        )
    }

    fun delete(id: Long) {
        val user = findUser(id)
        userRepository.delete(user)
    }

    private fun findUser(id: Long): User {
        return userRepository.findById(id)
            .orElseThrow {
                NoSuchElementException("User not found")
            }
    }
}