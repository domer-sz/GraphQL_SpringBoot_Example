package dev.sdom.graphql.demo.domain

import dev.sdom.graphql.demo.domain.ports.TrackRepository
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class TrackPublisher(private val trackRepository: TrackRepository) {

        final val publisher: Flowable<String> = Observable
                .intervalRange(1, 3500, 0, 1, TimeUnit.SECONDS)
                .map {
                        try {
                                trackRepository.getTrackName(it)
                        } catch (e: Throwable) {
                                "Brak utworu dla id: $it"
                        }
                }
                .toFlowable(BackpressureStrategy.LATEST)
                .share()
}
