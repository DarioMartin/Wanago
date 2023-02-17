package com.architectcoders.wanago.data.datasource

interface LocationDataSource {
    suspend fun findLastRegion(): String?
}