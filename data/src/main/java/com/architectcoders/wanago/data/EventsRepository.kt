package com.architectcoders.wanago.data

import com.architectcoders.wanago.data.datasource.EventsLocalDataSource
import com.architectcoders.wanago.data.datasource.EventsRemoteDataSource
import com.architectcoders.wanago.domain.WanagoError
import com.architectcoders.wanago.domain.WanagoEvent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EventsRepository @Inject constructor(
    private val regionRepository: RegionRepository,
    private val localDataSource: EventsLocalDataSource,
    private val remoteDataSource: EventsRemoteDataSource
) {

    val nearbyEvents = localDataSource.events

    suspend fun requestNearbyEvents(forceUpdate: Boolean): WanagoError? {
        if (localDataSource.isEmpty() || forceUpdate) {
            val events = remoteDataSource.findNearbyEvents(regionRepository.findLastRegion())
            events.fold(ifLeft = { return it }) { localDataSource.save(it) }
        }
        return null
    }

    fun getEventById(id: String): Flow<WanagoEvent> = localDataSource.getById(id)
}
