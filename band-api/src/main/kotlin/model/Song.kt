package com.example.bandapi.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "songs")
class Song(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var album: String,

    @Column(nullable = false)
    var year: Int,

    @Column(nullable = false)
    var duration: Int,

    @Column(nullable = false)
    var genre: String,

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    val author: User
)