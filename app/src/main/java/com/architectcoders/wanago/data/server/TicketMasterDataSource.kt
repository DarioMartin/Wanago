package com.architectcoders.wanago.data.server

import arrow.core.Either
import com.architectcoders.wanago.data.datasource.EventsRemoteDataSource
import com.architectcoders.wanago.domain.WanagoEvent
import com.architectcoders.wanago.domain.WanagoError
import com.architectcoders.wanago.domain.tryCall

class TicketMasterDataSource(private val apiKey: String) : EventsRemoteDataSource {

    override suspend fun findNearbyEvents(region: String): Either<WanagoError, List<WanagoEvent>> = tryCall {
        RemoteConnection.service
            .listNearbyEvents(apiKey, region)
            .embedded.events
            .toDomainModel()
    }
}

private fun List<RemoteEvent>.toDomainModel(): List<WanagoEvent> = map { it.toDomainModel() }

private fun RemoteEvent.toDomainModel(): WanagoEvent =
    WanagoEvent(
        id,
        name,
        if (images.isNotEmpty()) images[0].url else "",
        if (embedded.venues.isNotEmpty()) embedded.venues[0].name else "TBD",
    )
