package dev.sdom.graphql.demo.adapters.primary.graphql

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.coxautodev.graphql.tools.GraphQLResolver
import com.coxautodev.graphql.tools.GraphQLSubscriptionResolver
import dev.sdom.graphql.demo.domain.*
import dev.sdom.graphql.demo.domain.ports.AlbumRepository
import dev.sdom.graphql.demo.domain.ports.ArtistRepository
import dev.sdom.graphql.demo.domain.ports.RatingServiceClient
import dev.sdom.graphql.demo.domain.ports.TrackRepository
import org.reactivestreams.Publisher
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.concurrent.CompletableFuture


@Service
class GraphQlQuery(private val artistRepository: ArtistRepository, private val albumRepository: AlbumRepository) : GraphQLQueryResolver {
        fun artists(offset: Long, limit: Long) = artistRepository.getArtists(offset, limit)
        fun album(albumId: String) = albumRepository.getAlbum(albumId)
}

@Service
class GraphQlArtistResolver(private val ratingServiceClient: RatingServiceClient) : GraphQLResolver<Artist> {
        fun albums(artist: Artist, limit: Int): List<Album> = artist.albums.limit(limit)
        fun rating(artist: Artist): String = ratingServiceClient.getSyncRatingForArtist(artist.id)
        fun asyncRating(artist: Artist): CompletableFuture<String> = ratingServiceClient.getAsyncRatingForArtist(artist.id)
}

@Service
class GraphQlAlbumResolver(private val ratingServiceClient: RatingServiceClient) : GraphQLResolver<Album> {
        fun tracks(album: Album, limit: Int): List<Track> = album.tracks.limit(limit)
        fun rating(album: Album): String = ratingServiceClient.getSyncRatingForAlbum(album.id)
        fun asyncRating(album: Album): CompletableFuture<String> = ratingServiceClient.getAsyncRatingForAlbum(album.id)
}

@Service
class MySubscription(val trackPublisher: TrackPublisher) : GraphQLSubscriptionResolver {
        fun tracks(): Publisher<String> = trackPublisher.publisher
}

@Service
class GraphQlMutation(private val trackRepository: TrackRepository) : GraphQLMutationResolver {
        fun addTrackToAlbum(albumId: Long, name: String, genre: String, composer: String, unitPrice: BigDecimal) =
                trackRepository.save(albumId, name, genre, composer, unitPrice)
}


