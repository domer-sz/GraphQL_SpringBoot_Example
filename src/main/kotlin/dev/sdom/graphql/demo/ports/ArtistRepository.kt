package dev.sdom.graphql.demo.ports

interface ArtistRepository {
        fun getArtist(artistId: ArtistId): Artist
}