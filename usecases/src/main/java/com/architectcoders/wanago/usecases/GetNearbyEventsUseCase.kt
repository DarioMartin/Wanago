package com.architectcoders.wanago.usecases

import androidx.paging.PagingData
import com.architectcoders.wanago.data.EventsRepository
import com.architectcoders.wanago.domain.WanagoEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNearbyEventsUseCase @Inject constructor(private val repository: EventsRepository) {
    suspend operator fun invoke(coroutineScope: CoroutineScope): Flow<PagingData<WanagoEvent>> =
        repository.requestNearbyEvents(coroutineScope)
}
