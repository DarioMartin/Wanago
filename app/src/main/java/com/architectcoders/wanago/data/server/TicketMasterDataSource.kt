package com.architectcoders.wanago.data.server

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.architectcoders.wanago.data.datasource.EventsRemoteDataSource
import com.architectcoders.wanago.data.server.model.RemoteEvent
import com.architectcoders.wanago.di.ApiKey
import com.architectcoders.wanago.di.NetworkPageSize
import com.architectcoders.wanago.domain.WanagoEvent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TicketMasterDataSource @Inject constructor(
  @ApiKey private val apiKey: String,
  @NetworkPageSize private val networkPageSize: Int,
  private val service: RemoteService
) : EventsRemoteDataSource {

    override fun findNearbyEvents(region: String): Flow<PagingData<WanagoEvent>> {
        return Pager(
            config = PagingConfig(pageSize = networkPageSize, enablePlaceholders = false),
            pagingSourceFactory = {
                EventsPagingSource(apiKey, networkPageSize, service, region)
            }
        ).flow
    }

    override suspend fun getEventById(id: String): WanagoEvent {
        return service.getEventById(id, apiKey).toDomainModel()
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
