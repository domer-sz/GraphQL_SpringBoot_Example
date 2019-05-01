package dev.sdom.graphql.demo.adapters.secondary.mysql

import dev.sdom.graphql.demo.domain.Artist
import dev.sdom.graphql.demo.domain.Genre
import dev.sdom.graphql.demo.domain.Track
import dev.sdom.graphql.demo.domain.ports.ArtistRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.ResultSetExtractor
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.sql.ResultSet

typealias ArtistId = String
typealias AlbumId = String

@Repository
class MySqlArtistRepository(private val jdbcTemplate: JdbcTemplate) : ArtistRepository {

        override fun getArtists(offset: Long, limit: Long): List<Artist>? = jdbcTemplate.query(
         """
            SELECT Album.AlbumId, Album.Title, LimitedArtist.Name as ArtistName, LimitedArtist.ArtistId, Track.TrackId, Track.Name as TrackName, Track.TrackId, Genre.Name as GenreName, Track.Composer, Track.UnitPrice
            FROM (SELECT * FROM Chinook.Artist LIMIT ?, ?) as LimitedArtist
                INNER JOIN Chinook.Album on Album.ArtistId = LimitedArtist.ArtistId
                INNER JOIN Chinook.Track on Album.AlbumId = Track.AlbumId
                INNER JOIN Chinook.Genre on Track.GenreId = Genre.GenreId
         """.trimIndent(),
                arrayOf(offset, limit),
                ResultSetExtractor { rs: ResultSet ->
                        val artistMap = mutableMapOf<ArtistId, DbArtist>()
                        val albumMap = mutableMapOf<AlbumId, DbAlbum>()

                        while (rs.next()) {
                                val artistId: ArtistId = rs.getString("ArtistId")
                                val artist = artistMap.getOrPut(artistId) { DbArtist(artistId, rs.getString("ArtistName")) }

                                val albumId: AlbumId = rs.getString("AlbumId")
                                if (!albumMap.containsKey(albumId)) {
                                        val album = DbAlbum(albumId, rs.getString("Title"))
                                        albumMap[albumId] = album
                                        artist.addAlbum(album)
                                        album
                                } else { albumMap[albumId] }?.addTrack(Track(
                                        rs.getLong("TrackId"),
                                        rs.getString("TrackName"),
                                        Genre(rs.getString("GenreName")),
                                        rs.getString("Composer"),
                                        BigDecimal.valueOf(rs.getDouble("UnitPrice"))
                                ))
                        }

                        artistMap.values.map { it.toDomain() }
                }.also {
                        logger.info("getArtists")
                }
        )

        companion object {
                val logger: Logger = LoggerFactory.getLogger(this::class.java)
        }
}