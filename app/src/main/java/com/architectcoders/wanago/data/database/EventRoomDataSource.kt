package com.architectcoders.wanago.data.database

import com.architectcoders.wanago.data.datasource.EventsLocalDataSource
import com.architectcoders.wanago.domain.WanagoError
import com.architectcoders.wanago.domain.WanagoEvent
import com.architectcoders.wanago.domain.tryCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.architectcoders.wanago.data.database.Event as DbEvent

class EventRoomDataSource @Inject constructor(private val eventDao: EventDao) :
    EventsLocalDataSource {

    override val events: Flow<List<WanagoEvent>> = eventDao.getAll().map { it.toDomainModel() }

    override suspend fun isEmpty(): Boolean = eventDao.eventCount() == 0

    override fun getById(id: String): Flow<WanagoEvent> =
        eventDao.getById(id).map { it.toDomainModel() }

    override suspend fun save(events: List<WanagoEvent>): WanagoError? = tryCall {
        eventDao.insertEvents(events.fromDomainModel())
    }.fold(ifLeft = { it }, ifRight = { null })
}

private fun List<DbEvent>.toDomainModel(): List<WanagoEvent> = map { it.toDomainModel() }

private fun DbEvent.toDomainModel(): WanagoEvent = WanagoEvent(
    id, name, imageUrl, venue, isFavorite
)

private fun List<WanagoEvent>.fromDomainModel(): List<DbEvent> = map { it.fromDomainModel() }

private fun WanagoEvent.fromDomainModel(): DbEvent = DbEvent(
    id, name, imageUrl, venue, isFavorite
)
