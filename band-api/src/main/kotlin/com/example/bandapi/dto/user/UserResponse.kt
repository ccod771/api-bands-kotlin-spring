package com.example.bandapi.dto.user

import com.example.bandapi.model.User
import java.time.LocalDateTime

data class UserResponse(
    val id: Long,
    val username: String,
    val email: String,
    val createdAt: LocalDateTime
) {
    companion object {

        fun from(user: User): UserResponse =
            UserResponse(
                id = user.id,
                username = user.username,
                email = user.email,
                createdAt = user.createdAt
            )
    }
}