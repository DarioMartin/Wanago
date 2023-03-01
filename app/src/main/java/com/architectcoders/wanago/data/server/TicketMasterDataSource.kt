package com.architectcoders.wanago.data.server

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.architectcoders.wanago.data.datasource.EventsRemoteDataSource
import com.architectcoders.wanago.data.server.model.RemoteEvent
import com.architectcoders.wanago.di.ApiKey
import com.architectcoders.wanago.domain.WanagoEvent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

const val NETWORK_PAGE_SIZE = 25

class TicketMasterDataSource @Inject constructor(@ApiKey private val apiKey: String) :
    EventsRemoteDataSource {

    override fun findNearbyEvents(region: String): Flow<PagingData<WanagoEvent>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = {
                EventsPagingSource(service = RemoteConnection.service, region, apiKey)
            }
        ).flow
    }

    override suspend fun getEventById(id: String): WanagoEvent {
        return RemoteConnection.service.getEventById(id, apiKey).toDomainModel()
    }
}

fun RemoteEvent.toDomainModel(): WanagoEvent = WanagoEvent(
    id,
    name,
    if (images.isNotEmpty()) images[0].url else "",
    if (embedded.venues.isNotEmpty()) embedded.venues[0].name else "TBD",
    if (dates.start.localDate != null && dates.start.localTime != null)
        "${dates.start.localDate} ${dates.start.localTime}" else "TBD",
    pleaseNote ?: ""
)
