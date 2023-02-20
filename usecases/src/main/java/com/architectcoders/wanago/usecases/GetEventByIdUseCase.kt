package com.architectcoders.wanago.usecases

import com.architectcoders.wanago.data.EventsRepository
import javax.inject.Inject

class GetEventByIdUseCase @Inject constructor(private val repository: EventsRepository) {
    operator fun invoke(eventId: String) = repository.getEventById(eventId)
}

