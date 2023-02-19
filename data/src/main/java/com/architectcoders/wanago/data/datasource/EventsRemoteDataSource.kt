package com.architectcoders.wanago.data.datasource

import androidx.paging.PagingData
import com.architectcoders.wanago.domain.WanagoEvent
import kotlinx.coroutines.flow.Flow

interface EventsRemoteDataSource {
    fun findNearbyEvents(region: String): Flow<PagingData<WanagoEvent>>
}
