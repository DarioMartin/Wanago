package com.architectcoders.wanago.data

import com.architectcoders.wanago.data.datasource.EventsRemoteDataSource
import com.architectcoders.wanago.domain.Error
import com.architectcoders.wanago.domain.Event
import com.architectcoders.wanago.domain.tryCall

class EventsRepository (
    private val regionRepository: RegionRepository,
    private val remoteDataSource: EventsRemoteDataSource) {

    var nearbyEvents: MutableList<Event> = mutableListOf()

    suspend fun requestNearbyEvents(): Error? = tryCall {
        // Update the same list because the adapter is subscribed to it
        nearbyEvents.clear()
        nearbyEvents.addAll(remoteDataSource.findNearbyEvents(regionRepository.findLastRegion()))
    }

    fun getEventById(id: String): Event? = nearbyEvents.filter{ event -> event.id == id }.getOrNull(0)
}
