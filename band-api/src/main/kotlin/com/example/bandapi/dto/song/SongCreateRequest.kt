package com.example.bandapi.dto.song

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class SongCreateRequest(

    @field:NotBlank
    val title: String,

    @field:NotBlank
    val album: String,

    @field:Min(1)
    val year: Int,

    @field:Min(1)
    val duration: Int,

    @field:NotBlank
    val genre: String
)