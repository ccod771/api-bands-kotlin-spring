package com.example.bandapi.controller

import com.example.bandapi.dto.song.SongCreateRequest
import com.example.bandapi.dto.song.SongResponse
import com.example.bandapi.service.SongService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/songs")
class SongController(
    private val songService: SongService
) {

    @GetMapping
    fun findAll(): List<SongResponse> {
        return songService.findAll()
    }

    @GetMapping("/{id}")
    fun findById(
        @PathVariable id: Long
    ): SongResponse {
        return songService.findById(id)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @Valid @RequestBody request: SongCreateRequest,
        @AuthenticationPrincipal userDetails: UserDetails
    ): SongResponse {
        return songService.create(
            request,
            userDetails
        )
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody request: SongCreateRequest,
        @AuthenticationPrincipal userDetails: UserDetails
    ): SongResponse {
        return songService.update(
            id,
            request,
            userDetails
        )
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(
        @PathVariable id: Long,
        @AuthenticationPrincipal userDetails: UserDetails
    ) {
        songService.delete(
            id,
            userDetails
        )
    }
}