package dev.sdom.graphql.demo.domain

import java.math.BigDecimal

data class Album(val id: String, val title: String, val tracks: List<Track>)
data class Artist(val id: String, val name: String, val albums: List<Album>)
data class Track(
        val id: Long,
        val name: String,
        val genre: Genre,
        val composer: String?,
        val unitPrice: BigDecimal
)

data class Genre(val name: String)

fun <T> List<T>.limit(limit: Int) = this.subList(
        0,
        when {
                limit <= this.size && limit > 0 -> limit
                else -> this.size

        }
)