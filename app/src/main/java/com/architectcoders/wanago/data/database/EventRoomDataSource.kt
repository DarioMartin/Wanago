package com.architectcoders.wanago.data.database

import com.architectcoders.wanago.data.datasource.EventsLocalDataSource
import com.architectcoders.wanago.data.database.Event as DbEvent
import com.architectcoders.wanago.domain.Event
import com.architectcoders.wanago.domain.WanagoError
import com.architectcoders.wanago.domain.tryCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EventRoomDataSource(private val eventDao: EventDao) : EventsLocalDataSource {

    override val events: Flow<List<Event>> = eventDao.getAll().map { it.toDomainModel() }

    override suspend fun isEmpty(): Boolean = eventDao.eventCount() == 0

    override fun getById(id: String): Flow<Event> = eventDao.getById(id).map { it.toDomainModel() }

    override suspend fun save(events: List<Event>): WanagoError? = tryCall {
        eventDao.insertEvents(events.fromDomainModel())
    }.fold(
        ifLeft = { it },
        ifRight = { null }
    )
}

private fun List<DbEvent>.toDomainModel(): List<Event> = map { it.toDomainModel() }

private fun DbEvent.toDomainModel(): Event =
    Event(
        id,
        name,
        imageUrl,
        venue,
        isFavorite)

private fun List<Event>.fromDomainModel(): List<DbEvent> = map { it.fromDomainModel() }

private fun Event.fromDomainModel(): DbEvent = DbEvent(
    id,
    name,
    imageUrl,
    venue,
    isFavorite
)
