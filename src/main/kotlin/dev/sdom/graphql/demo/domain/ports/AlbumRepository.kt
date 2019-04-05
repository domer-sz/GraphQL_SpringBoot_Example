package dev.sdom.graphql.demo.domain.ports

import dev.sdom.graphql.demo.domain.Album

interface AlbumRepository {
        fun getAlbum(albumId: String): Album?
}