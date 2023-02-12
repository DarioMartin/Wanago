package com.architectcoders.wanago.data.datasource

import com.architectcoders.wanago.domain.Event

interface EventsRemoteDataSource {
    suspend fun findNearbyEvents(region: String): List<Event>
}