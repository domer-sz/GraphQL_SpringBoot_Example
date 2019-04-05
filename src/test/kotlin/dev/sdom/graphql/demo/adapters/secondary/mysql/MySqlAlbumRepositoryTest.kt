package dev.sdom.graphql.demo.adapters.secondary.mysql

import dev.sdom.graphql.demo.BaseIntegrationTest
import dev.sdom.graphql.demo.domain.ports.AlbumRepository
import org.junit.Test

import org.springframework.beans.factory.annotation.Autowired

class MySqlAlbumRepositoryTest : BaseIntegrationTest() {

        @Autowired
        lateinit var albumRepository: AlbumRepository

        @Test
        fun getAlbum() {
                val albumWithTracks = albumRepository.getAlbum("1")
                print(albumWithTracks)
        }
}