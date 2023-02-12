package com.architectcoders.wanago.data

import arrow.core.getOrElse
import arrow.core.left
import arrow.core.right
import com.architectcoders.wanago.App
import com.architectcoders.wanago.R
import com.architectcoders.wanago.data.server.TicketMasterDataSource
import com.architectcoders.wanago.domain.Error
import com.architectcoders.wanago.domain.Event
import com.architectcoders.wanago.domain.tryCall

class EventsRepository (application: App) {
    private val remoteDataSource = TicketMasterDataSource(application.getString(R.string.api_key))

    var nearbyEvents: MutableList<Event> = mutableListOf()

    suspend fun requestNearbyEvents(): Error? = tryCall {
        // Update the same list because the adapter is subscribed to it
        nearbyEvents.clear()
        nearbyEvents.addAll(remoteDataSource.findNearbyEvents("Madrid"))
    }
}