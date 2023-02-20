package com.architectcoders.wanago.data

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.architectcoders.wanago.data.datasource.EventsLocalDataSource
import com.architectcoders.wanago.data.datasource.EventsRemoteDataSource
import com.architectcoders.wanago.domain.WanagoError
import com.architectcoders.wanago.domain.WanagoEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EventsRepository @Inject constructor(
    private val regionRepository: RegionRepository,
    private val localDataSource: EventsLocalDataSource,
    private val remoteDataSource: EventsRemoteDataSource
) {

    fun getFavoriteEvents() = localDataSource.events.map {
        it.filter { event -> event.isFavorite }
    }

    suspend fun requestNearbyEvents(coroutineScope: CoroutineScope): Flow<PagingData<WanagoEvent>> {
        val favoriteEventsFlow = getFavoriteEvents()

        val nearbyEventsFlow = remoteDataSource.findNearbyEvents(regionRepository.findLastRegion())
            .cachedIn(coroutineScope)

        val combinedFlow =
            nearbyEventsFlow.combine(favoriteEventsFlow) { remoteEvents, favoriteEvents ->
                val combinedEvents = remoteEvents.map { remoteEvent ->
                    val isFavorite = favoriteEvents.any { it.id == remoteEvent.id }
                    remoteEvent.copy(isFavorite = isFavorite)
                }
                combinedEvents
            }

        return combinedFlow
    }

    fun getEventById(id: String): Flow<WanagoEvent> = localDataSource.getById(id)

    suspend fun switchFavorite(event: WanagoEvent): WanagoError? {
        val updatedEvent = event.copy(isFavorite = !event.isFavorite)
        return localDataSource.save(listOf(updatedEvent))
    }
}
