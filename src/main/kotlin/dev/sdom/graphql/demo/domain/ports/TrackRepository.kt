package dev.sdom.graphql.demo.domain.ports

import dev.sdom.graphql.demo.domain.Track
import java.math.BigDecimal

interface TrackRepository {
        fun getTrackName(trackId: Long): String
        fun save(albumId: Long, name: String, genre: String, composer: String, unitPrice: BigDecimal): Track
}