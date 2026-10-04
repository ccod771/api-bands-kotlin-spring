package com.example.bandapi.service

import com.example.bandapi.dto.song.SongCreateRequest
import com.example.bandapi.dto.song.SongResponse
import com.example.bandapi.model.Song
import com.example.bandapi.repository.SongRepository
import com.example.bandapi.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class SongService(
    private val songRepository: SongRepository,
    private val userRepository: UserRepository
) {

    fun create(
        request: SongCreateRequest,
        userDetails: UserDetails
    ): SongResponse {

        val user = findUserByEmail(userDetails.username)

        val song = Song(
            title = request.title,
            album = request.album,
            year = request.year,
            duration = request.duration,
            genre = request.genre,
            author = user
        )

        return SongResponse.from(
            songRepository.save(song)
        )
    }

    fun findAll(): List<SongResponse> {
        return songRepository.findAll()
            .map(SongResponse::from)
    }

    fun findById(id: Long): SongResponse {
        return SongResponse.from(
            findSong(id)
        )
    }

    fun update(
        id: Long,
        request: SongCreateRequest,
        userDetails: UserDetails
    ): SongResponse {

        val song = findSong(id)

        verifyOwnership(song, userDetails)

        song.title = request.title
        song.album = request.album
        song.year = request.year
        song.duration = request.duration
        song.genre = request.genre

        return SongResponse.from(
            songRepository.save(song)
        )
    }

    fun delete(
        id: Long,
        userDetails: UserDetails
    ) {

        val song = findSong(id)

        verifyOwnership(song, userDetails)

        songRepository.delete(song)
    }

    private fun findSong(id: Long): Song {

        return songRepository.findById(id)
            .orElseThrow {
                NoSuchElementException("Song not found")
            }
    }

    private fun findUserByEmail(email: String) =
        userRepository.findByEmail(email)
            ?: throw NoSuchElementException("User not found")

    private fun verifyOwnership(
        song: Song,
        userDetails: UserDetails
    ) {

        if (song.author.email != userDetails.username) {
            throw org.springframework.security.access.AccessDeniedException(
                "You are not the author of this song"
            )
        }
    }
}