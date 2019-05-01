package dev.sdom.graphql.demo.adapters.secondary.mysql

import dev.sdom.graphql.demo.domain.Genre
import dev.sdom.graphql.demo.domain.Track
import dev.sdom.graphql.demo.domain.ports.TrackRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
class MySqlTrackRepository(private val jdbcTemplate: JdbcTemplate) : TrackRepository {

        override fun getTrackName(trackId: Long): String =
                jdbcTemplate.queryForObject("SELECT Name FROM Chinook.Track where TrackId = ?", arrayOf(trackId), String::class.java)


        override fun save(albumId: Long, name: String, genre: String, composer: String, unitPrice: BigDecimal): Track {

                val trackId = getNewTrackId()
                val genreId = getGenreId(genre)
                jdbcTemplate.update(
                        "INSERT INTO Track (TrackId, Name, AlbumId, UnitPrice, Composer, GenreId, MediaTypeId, Milliseconds, Bytes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                        trackId, name, albumId, unitPrice, composer, genreId, 1, 0, 0
                )
                return Track(trackId, name, Genre(genre), composer, unitPrice)
        }

        private fun getNewTrackId() =
                jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Chinook.Track", Long::class.java)?.plus(1)
                        ?: throw RuntimeException("Can't insert new Track")

        private fun getGenreId(genre: String): Long {
                return try {
                        jdbcTemplate.queryForObject("SELECT GenreId FROM Chinook.GENRE where Name= ?", arrayOf(genre), Long::class.java)
                } catch (ex: Exception) {
                        ex.printStackTrace()
                        val newGenreId = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Chinook.Genre", Long::class.java)?.plus(1)
                        jdbcTemplate.update(
                                "INSERT INTO Genre (GenreId, Name) VALUES (?, ?)",
                                newGenreId
                                        ?: throw RuntimeException("Can't insert new Genre"), genre
                        )
                        return newGenreId
                }
        }
}