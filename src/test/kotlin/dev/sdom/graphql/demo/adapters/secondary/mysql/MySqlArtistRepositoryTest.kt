package dev.sdom.graphql.demo.adapters.secondary.mysql

import dev.sdom.graphql.demo.BaseIntegrationTest
import dev.sdom.graphql.demo.domain.ports.AlbumRepository
import org.junit.Test

import org.springframework.beans.factory.annotation.Autowired

class MySqlArtistRepositoryTest : BaseIntegrationTest() {

        @Autowired
        lateinit var artistRepository: MySqlArtistRepository

        @Test
        fun getArtist(){
                val artists = artistRepository.getArtists(0, 50)
                print(artists)

        }
}