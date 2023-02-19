package com.architectcoders.wanago.usecases

import com.architectcoders.wanago.data.EventsRepository
import javax.inject.Inject

class GetNearbyEventsUseCase @Inject constructor(private val repository: EventsRepository) {
    operator fun invoke() = repository.nearbyEvents
}

