package dev.sdom.graphql.demo.domain.ports

import dev.sdom.graphql.demo.domain.Artist

interface ArtistRepository {
        fun getArtists(offset: Long, limit: Long): List<Artist>?
}