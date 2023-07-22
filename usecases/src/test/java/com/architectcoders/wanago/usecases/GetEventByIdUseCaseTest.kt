package com.architectcoders.wanago.usecases

import com.architectcoders.wanago.data.EventsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify

internal class GetEventByIdUseCaseTest {

  @Test
  fun `when use case is invoked then events repository is called`(): Unit = runBlocking {
    val eventId = "1"
    val repository: EventsRepository = mock()
    val getEventByIdUseCase = GetEventByIdUseCase(repository)

    getEventByIdUseCase(eventId)

    verify(repository).getEventById(eventId)
  }
}
