package com.architectcoders.wanago.data

import com.architectcoders.wanago.data.datasource.EventsLocalDataSource
import com.architectcoders.wanago.data.datasource.EventsRemoteDataSource
import com.architectcoders.wanago.testshared.sampleEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
internal class EventsRepositoryTest {

  private val regionRepository: RegionRepository = mock()
  private val localDataSource: EventsLocalDataSource = mock()
  private val remoteDataSource: EventsRemoteDataSource = mock()

  private lateinit var eventsRepository: EventsRepository

  @Before
  fun setUp() {
    eventsRepository = EventsRepository(regionRepository, localDataSource, remoteDataSource)
  }

  @Test
  fun `given the same event is retrieved from local and remote source when getEventById is called then a flow with the event is returned and isFavourite flag comes from local source`(): Unit = runBlocking {
    val eventId = "1"
    val remoteSourceEvent = sampleEvent.copy("1")
    val localSourceEvent = remoteSourceEvent.copy(isFavorite = !remoteSourceEvent.isFavorite)
    whenever(localDataSource.events) doReturn flowOf(listOf(localSourceEvent))
    whenever(remoteDataSource.getEventById(eventId)) doReturn remoteSourceEvent

    val result = eventsRepository.getEventById(eventId)

    assertEquals(result.first(), localSourceEvent)
  }

  @Test
  fun `when switch to favourite is called then event should be saved into local data source with isFavourite flag switched`(): Unit = runBlocking {
    val eventToSwitch = sampleEvent.copy(id = "1")
    val eventWithFavSwitched = eventToSwitch.copy(isFavorite = !eventToSwitch.isFavorite)

    eventsRepository.switchFavorite(eventToSwitch)

    verify(localDataSource).save(listOf(eventWithFavSwitched))
  }

}
