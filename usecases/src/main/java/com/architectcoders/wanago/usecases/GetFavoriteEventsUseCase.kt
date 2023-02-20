package com.architectcoders.wanago.usecases

import com.architectcoders.wanago.data.EventsRepository
import javax.inject.Inject

class GetFavoriteEventsUseCase @Inject constructor(private val repository: EventsRepository) {
    operator fun invoke() = repository.getFavoriteEvents()
}

