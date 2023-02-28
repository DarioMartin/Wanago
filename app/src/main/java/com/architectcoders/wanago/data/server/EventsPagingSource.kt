package com.architectcoders.wanago.data.server

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.architectcoders.wanago.domain.WanagoEvent
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 0


class EventsPagingSource(
    private val service: RemoteService, private val region: String, private val apiKey: String
) : PagingSource<Int, WanagoEvent>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, WanagoEvent> {
        val pageIndex = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = service.listNearbyEvents(apiKey, region, pageIndex, NETWORK_PAGE_SIZE)

            val events = response.embedded?.events ?: emptyList()
            val nextPage = if (events.isEmpty()) null else response.page.number + 1

            LoadResult.Page(
                data = events.map { it.toDomainModel() },
                prevKey = if (pageIndex == STARTING_PAGE_INDEX) null else pageIndex,
                nextKey = nextPage
            )

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, WanagoEvent>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
