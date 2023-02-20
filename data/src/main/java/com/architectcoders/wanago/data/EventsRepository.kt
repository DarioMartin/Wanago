package com.architectcoders.wanago.data

import androidx.paging.PagingData
import com.architectcoders.wanago.data.datasource.EventsLocalDataSource
import com.architectcoders.wanago.data.datasource.EventsRemoteDataSource
import com.architectcoders.wanago.domain.WanagoError
import com.architectcoders.wanago.domain.WanagoEvent
import kotlinx.coroutines.flow.Flow
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

    suspend fun requestNearbyEvents(): Flow<PagingData<WanagoEvent>> {
        return remoteDataSource.findNearbyEvents(regionRepository.findLastRegion())
    }

    fun getEventById(id: String): Flow<WanagoEvent> = localDataSource.getById(id)

    suspend fun switchFavorite(event: WanagoEvent): WanagoError? {
        val updatedEvent = event.copy(isFavorite = !event.isFavorite)
        return localDataSource.save(listOf(updatedEvent))
    }
}
