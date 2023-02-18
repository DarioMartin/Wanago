package com.architectcoders.wanago.data.datasource

import com.architectcoders.wanago.domain.WanagoError
import com.architectcoders.wanago.domain.Event
import kotlinx.coroutines.flow.Flow

interface EventsLocalDataSource {
    val events: Flow<List<Event>>

    suspend fun isEmpty(): Boolean
    fun getById(id: String): Flow<Event>
    suspend fun save(events: List<Event>): WanagoError?
}
