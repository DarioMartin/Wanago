package com.architectcoders.wanago.usecases

import com.architectcoders.wanago.data.EventsRepository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class GetNearbyEventsUseCase @Inject constructor(private val repository: EventsRepository) {
    suspend operator fun invoke(scope: CoroutineScope) = repository.requestNearbyEvents(scope)
}

