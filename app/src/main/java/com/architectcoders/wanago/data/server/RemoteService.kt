package com.architectcoders.wanago.data.server

import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteService {
    @GET("/discovery/v2/events")
    suspend fun listNearbyEvents(
        @Query("apikey") apiKey: String,
        @Query("city") city: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): RemoteSearchResult
}
