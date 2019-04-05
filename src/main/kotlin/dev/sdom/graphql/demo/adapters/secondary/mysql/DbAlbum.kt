package dev.sdom.graphql.demo.adapters.secondary.mysql

import dev.sdom.graphql.demo.domain.Album
import dev.sdom.graphql.demo.domain.Track

data class DbAlbum(val albumId: String, val albumTitle: String, val tracks: MutableList<Track> = mutableListOf()) {
        fun addTrack(track: Track) { this.tracks.add(track)}
        fun toDomain() = Album(albumId, albumTitle, tracks.toList())
}

fun List<DbAlbum>.toDomain(): List<Album> = this.map(DbAlbum::toDomain)