package dev.sdom.graphql.demo

import dev.sdom.graphql.demo.ports.ArtistId
import dev.sdom.graphql.demo.ports.ArtistRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
        val context = runApplication<DemoApplication>(*args)
        val bean = context.getBean(ArtistRepository::class.java)
        val artist = bean.getArtist(ArtistId("1"))
        print(artist)
}
