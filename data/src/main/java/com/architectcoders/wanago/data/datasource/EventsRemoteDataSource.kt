package com.architectcoders.wanago.data.datasource

import arrow.core.Either
import com.architectcoders.wanago.domain.Event
import com.architectcoders.wanago.domain.WanagoError

interface EventsRemoteDataSource {
    suspend fun findNearbyEvents(region: String): Either<WanagoError, List<Event>>
}
