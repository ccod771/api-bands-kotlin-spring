package com.example.bandapi.dto.song

import com.example.bandapi.model.Song
import java.time.LocalDateTime

data class SongResponse(

    val id: Long,
    val title: String,
    val album: String,
    val year: Int,
    val duration: Int,
    val genre: String,
    val createdAt: LocalDateTime,
    val authorId: Long,
    val authorUsername: String

) {
    companion object {

        fun from(song: Song): SongResponse =
            SongResponse(
                id = song.id,
                title = song.title,
                album = song.album,
                year = song.year,
                duration = song.duration,
                genre = song.genre,
                createdAt = song.createdAt,
                authorId = song.author.id,
                authorUsername = song.author.username
            )
    }
}