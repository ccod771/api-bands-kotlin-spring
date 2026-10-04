package com.example.bandapi.repository

import com.example.bandapi.model.Song
import org.springframework.data.jpa.repository.JpaRepository

interface SongRepository : JpaRepository<Song, Long> {

    fun findAllByAuthorId(authorId: Long): List<Song>
}