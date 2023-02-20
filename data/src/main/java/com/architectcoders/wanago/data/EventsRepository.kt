package com.architectcoders.wanago.data

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.architectcoders.wanago.data.datasource.EventsLocalDataSource
import com.architectcoders.wanago.data.datasource.EventsRemoteDataSource
import com.architectcoders.wanago.domain.WanagoError
import com.architectcoders.wanago.domain.WanagoEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
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

    fun getEventById(id: String): Flow<WanagoEvent> {
        return flow {
            emit(remoteDataSource.getEventById(id))
        }.transformLatest { event ->
            getFavoriteEvents().collect { favoriteEvents ->
                val isFavorite = favoriteEvents.any { it.id == id }
                emit(event.copy(isFavorite = isFavorite))
            }
        }
    }

    suspend fun switchFavorite(event: WanagoEvent): WanagoError? {
        val updatedEvent = event.copy(isFavorite = !event.isFavorite)
        return localDataSource.save(listOf(updatedEvent))
    }
}
