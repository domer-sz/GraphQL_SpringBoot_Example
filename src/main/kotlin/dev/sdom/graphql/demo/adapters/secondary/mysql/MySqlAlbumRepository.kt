package dev.sdom.graphql.demo.adapters.secondary.mysql

import dev.sdom.graphql.demo.domain.*
import dev.sdom.graphql.demo.domain.ports.AlbumRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.ResultSetExtractor
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.sql.ResultSet

@Repository
class MySqlAlbumRepository(private val jdbcTemplate: JdbcTemplate) : AlbumRepository {

        override fun getAlbum(albumId: String): Album? = jdbcTemplate.query(
        """
            Select Album.AlbumId, Album.Title, Artist.Name as ArtistName, Artist.ArtistId, Track.TrackId, Track.Name as TrackName, Track.TrackId, Genre.Name as GenreName, Track.Composer, Track.UnitPrice
            FROM Album
                INNER JOIN Artist on Album.ArtistId = Artist.ArtistId
                INNER JOIN Track on Album.AlbumId = Track.AlbumId
                INNER JOIN Genre on Track.GenreId = Genre.GenreId
                WHERE Album.AlbumId = ?
         """.trimIndent(),
                 arrayOf(albumId),
                 ResultSetExtractor { rs: ResultSet ->
                         val tracks = mutableListOf<Track>()
                         var album: DbAlbum? = null
                         while (rs.next()) {
                                 if(rs.isFirst) album = DbAlbum(albumId, rs.getString("Title"))
                                 tracks.add(Track(
                                         rs.getLong("TrackId"),
                                         rs.getString("TrackName"),
                                         Genre(rs.getString("GenreName")),
                                         rs.getString("Composer"),
                                         BigDecimal.valueOf(rs.getDouble("UnitPrice"))
                                 ))
                         }
                         album!!.copy(tracks = tracks).toDomain()
                 }
         )
}