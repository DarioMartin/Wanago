package com.architectcoders.wanago.data.datasource

import com.architectcoders.wanago.domain.WanagoError
import com.architectcoders.wanago.domain.WanagoEvent
import kotlinx.coroutines.flow.Flow

interface EventsLocalDataSource {
    val events: Flow<List<WanagoEvent>>
    val favoriteEvents: Flow<List<WanagoEvent>>

    suspend fun isEmpty(): Boolean
    fun getById(id: String): Flow<WanagoEvent>
    suspend fun save(events: List<WanagoEvent>): WanagoError?
}
