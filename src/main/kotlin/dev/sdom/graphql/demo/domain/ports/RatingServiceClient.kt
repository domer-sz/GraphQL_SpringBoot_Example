package dev.sdom.graphql.demo.domain.ports

import java.util.concurrent.CompletableFuture

interface RatingServiceClient {
        fun getSyncRatingForArtist(artistId: String): String
        fun getSyncRatingForAlbum(albumId: String): String

        fun getAsyncRatingForArtist(artistId: String): CompletableFuture<String>
        fun getAsyncRatingForAlbum(albumId: String): CompletableFuture<String>
}