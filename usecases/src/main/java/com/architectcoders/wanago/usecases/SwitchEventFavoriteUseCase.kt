package com.architectcoders.wanago.usecases

import com.architectcoders.wanago.data.EventsRepository
import com.architectcoders.wanago.domain.WanagoEvent
import javax.inject.Inject

class SwitchEventFavoriteUseCase @Inject constructor(private val repository: EventsRepository) {
    suspend operator fun invoke(event: WanagoEvent) = repository.switchFavorite(event)
}

