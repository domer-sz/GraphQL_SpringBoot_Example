package dev.sdom.graphql.demo.adapters.secondary.mysql

import dev.sdom.graphql.demo.ports.Artist
import dev.sdom.graphql.demo.ports.ArtistId
import dev.sdom.graphql.demo.ports.ArtistName
import dev.sdom.graphql.demo.ports.ArtistRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository


@Repository
class MySqlArtistRepository(private val jdbcTemplate: JdbcTemplate) : ArtistRepository{
        override fun getArtist(artistId: ArtistId): Artist = jdbcTemplate.query(
                        "SELECT ArtistId, Name from Artist WHERE ArtistId = ?", arrayOf<Any>(artistId.raw)
                ) { rs, rowNum -> Artist(ArtistId(rs.getString("ArtistId")), ArtistName(rs.getString("Name"))) }.first()

}