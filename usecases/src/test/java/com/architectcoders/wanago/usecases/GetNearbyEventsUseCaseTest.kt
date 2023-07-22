package com.architectcoders.wanago.usecases

import com.architectcoders.wanago.data.EventsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestScope
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
internal class GetNearbyEventsUseCaseTest {

  @Test
  fun `when use case is invoked then events repository is called`(): Unit = runBlocking {
    val repository: EventsRepository = mock()
    val getNearbyEventsUseCase = GetNearbyEventsUseCase(repository)
    val testScope = TestScope()

    getNearbyEventsUseCase(testScope)

    verify(repository).requestNearbyEvents(testScope)
  }

}
