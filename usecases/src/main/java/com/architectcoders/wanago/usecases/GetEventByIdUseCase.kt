package com.architectcoders.wanago.usecases

import com.architectcoders.wanago.data.EventsRepository
import com.architectcoders.wanago.domain.WanagoEvent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEventByIdUseCase @Inject constructor(private val repository: EventsRepository) {
    operator fun invoke(eventId: String): Flow<WanagoEvent> = repository.getEventById(eventId)
}
