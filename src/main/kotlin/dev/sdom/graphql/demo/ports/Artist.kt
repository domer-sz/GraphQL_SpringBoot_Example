package dev.sdom.graphql.demo.ports

data class Artist(val id: ArtistId, val name: ArtistName)
inline class ArtistId(val raw: String)
inline class ArtistName(val raw: String)