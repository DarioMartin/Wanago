package com.architectcoders.wanago.presentation

import androidx.paging.PagingData
import com.architectcoders.wanago.data.PermissionChecker
import com.architectcoders.wanago.data.datasource.EventsLocalDataSource
import com.architectcoders.wanago.data.datasource.EventsRemoteDataSource
import com.architectcoders.wanago.data.datasource.LocationDataSource
import com.architectcoders.wanago.domain.WanagoError
import com.architectcoders.wanago.domain.WanagoEvent
import com.architectcoders.wanago.testshared.sampleEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf

val defaultEvents = listOf(
    sampleEvent.copy(id = "1"),
    sampleEvent.copy(id = "2"),
    sampleEvent.copy(id = "3"),
    sampleEvent.copy(id = "4")
)

class FakeRemoteDataSource : EventsRemoteDataSource {

    var events = defaultEvents

    override fun findNearbyEvents(region: String): Flow<PagingData<WanagoEvent>> {
        return flowOf(PagingData.from(events))
    }

    override suspend fun getEventById(id: String) = defaultEvents.first { it.id == id }

}

class FakeLocalDataSource : EventsLocalDataSource {

    val inMemoryEvents = MutableStateFlow<List<WanagoEvent>>(emptyList())
    override val events = inMemoryEvents

    private lateinit var findEventFlow: MutableStateFlow<WanagoEvent>

    override suspend fun isEmpty() = events.value.isEmpty()

    override fun getById(id: String): Flow<WanagoEvent> {
        findEventFlow = MutableStateFlow(inMemoryEvents.value.first { it.id == id })
        return findEventFlow
    }

    override suspend fun save(events: List<WanagoEvent>): WanagoError? {
        inMemoryEvents.value = events
        if (::findEventFlow.isInitialized) {
            events.firstOrNull { it.id == findEventFlow.value.id }?.let { findEventFlow.value = it }
        }
        return null
    }
}

class FakePermissionChecker : PermissionChecker {
    override fun check(permission: PermissionChecker.Permission) = true
}

class FakeLocationDataSource : LocationDataSource {
    var location = "US"

    override suspend fun findLastRegion(): String = location
}
