package com.architectcoders.wanago.data

import androidx.paging.PagingData
import androidx.paging.map
import com.architectcoders.wanago.data.datasource.EventsLocalDataSource
import com.architectcoders.wanago.data.datasource.EventsRemoteDataSource
import com.architectcoders.wanago.domain.WanagoEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class EventsRepository @Inject constructor(
    private val regionRepository: RegionRepository,
    private val localDataSource: EventsLocalDataSource,
    private val remoteDataSource: EventsRemoteDataSource
) {

    suspend fun requestNearbyEvents(): Flow<PagingData<WanagoEvent>> {
        return remoteDataSource.findNearbyEvents(regionRepository.findLastRegion())
            .combine(localDataSource.favoriteEvents) { remoteEvents, favoriteEvents ->
                val combinedEvents = remoteEvents.map { remoteEvent ->
                    val isFavorite = favoriteEvents.any { it.id == remoteEvent.id }
                    remoteEvent.copy(isFavorite = isFavorite)
                }
                combinedEvents
            }
    }

    fun getEventById(id: String): Flow<WanagoEvent> = localDataSource.getById(id)
}
