package com.architectcoders.wanago.data.datasource

import com.architectcoders.wanago.domain.model.Event

interface EventsRemoteDataSource {
    suspend fun findNearbyEvents(region: String): List<Event>
}