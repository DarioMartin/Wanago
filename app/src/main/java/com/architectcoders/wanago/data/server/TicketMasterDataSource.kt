package com.architectcoders.wanago.data.server

import arrow.core.Either
import com.architectcoders.wanago.data.datasource.EventsRemoteDataSource
import com.architectcoders.wanago.domain.Error
import com.architectcoders.wanago.domain.Event

class TicketMasterDataSource(private val apiKey: String) : EventsRemoteDataSource {

    override suspend fun findNearbyEvents(region: String): List<Event> =
        RemoteConnection.service
            .listNearbyEvents(apiKey, region)
            ._embedded.events
            .toDomainModel()
}

private fun List<RemoteEvent>.toDomainModel(): List<Event> = map { it.toDomainModel() }

private fun RemoteEvent.toDomainModel(): Event =
    Event(
        id,
        name,
        if (images.isNotEmpty()) images[0].url else "",
        if (_embedded.venues.isNotEmpty()) _embedded.venues[0].name else "TBD",
    )