package com.architectcoders.wanago.data.server

import com.architectcoders.wanago.data.server.model.RemoteEvent
import com.architectcoders.wanago.data.server.model.RemoteSearchResult

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RemoteService {
    @GET("/discovery/v2/events")
    suspend fun listNearbyEvents(
        @Query("apikey") apiKey: String,
        @Query("city") city: String,
        @Query("keyword") query: String?,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): RemoteSearchResult

    @GET("/discovery/v2/events/{id}")
    suspend fun getEventById(@Path("id") id: String, @Query("apikey") apiKey: String): RemoteEvent
}
