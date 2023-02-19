package com.architectcoders.wanago.usecases

import com.architectcoders.wanago.data.EventsRepository
import com.architectcoders.wanago.domain.WanagoError
import javax.inject.Inject

class RequestNearbyEventsUseCase @Inject constructor(
    private val repository: EventsRepository
) {
    suspend operator fun invoke(forcedUpdate: Boolean): WanagoError? = repository.requestNearbyEvents(forcedUpdate)
}

