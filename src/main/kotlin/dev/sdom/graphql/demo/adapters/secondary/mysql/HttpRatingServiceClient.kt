package dev.sdom.graphql.demo.adapters.secondary.mysql

import dev.sdom.graphql.demo.domain.ports.RatingServiceClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.util.concurrent.CompletableFuture

@Service
class HttpRatingServiceClient(private val restTemplate: RestTemplate) : RatingServiceClient {
        @Value("\${ratingService.baseUrl}")
        val ratingServiceBaseUrl: String? = null

        override fun getSyncRatingForArtist(artistId: String): String {
                return restTemplate.getForObject("$ratingServiceBaseUrl/rating/artist?id=$artistId", ArtistRating::class.java)?.rating
                        ?: ""
        }

        override fun getSyncRatingForAlbum(albumId: String): String {
                return restTemplate.getForObject("$ratingServiceBaseUrl/rating/album?id=$albumId", AlbumRating::class.java)?.rating
                        ?: ""
        }

        override fun getAsyncRatingForArtist(artistId: String): CompletableFuture<String> = CompletableFuture.supplyAsync {
                getSyncRatingForArtist(artistId)
        }

        override fun getAsyncRatingForAlbum(albumId: String): CompletableFuture<String> = CompletableFuture.supplyAsync {
                getSyncRatingForAlbum(albumId)
        }

        private data class AlbumRating(val albumId: String, val rating: String)
        private data class ArtistRating(val artistId: String, val rating: String)
}