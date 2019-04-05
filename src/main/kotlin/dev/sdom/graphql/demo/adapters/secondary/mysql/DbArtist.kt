package dev.sdom.graphql.demo.adapters.secondary.mysql

import dev.sdom.graphql.demo.domain.Artist

data class DbArtist(val artistId: String, val name: String, val albums: MutableList<DbAlbum> = mutableListOf()) {
        fun addAlbum(album: DbAlbum) { this.albums.add(album)}
        fun toDomain() = Artist(artistId, name, albums.toDomain())
}
