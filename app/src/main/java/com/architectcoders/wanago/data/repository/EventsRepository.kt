package com.architectcoders.wanago.data.repository

import com.architectcoders.wanago.App
import com.architectcoders.wanago.R
import com.architectcoders.wanago.data.server.TicketMasterDataSource
import com.architectcoders.wanago.domain.Error
import com.architectcoders.wanago.domain.model.Event
import com.architectcoders.wanago.domain.tryCall

object EventsRepository {
    private val remoteDataSource = TicketMasterDataSource("SzW9Zhsm9UBLMeNrXdCzyObBfv0Ublxp")

    var nearbyEvents: MutableList<Event> = mutableListOf()

    suspend fun requestNearbyEvents(): Error? = tryCall {
        // Update the same list because the adapter is subscribed to it
        nearbyEvents.clear()
        nearbyEvents.addAll(remoteDataSource.findNearbyEvents("Madrid"))
    }

    fun getEventById(id: String): Event? = nearbyEvents.filter{ event -> event.id == id }.getOrNull(0)
}