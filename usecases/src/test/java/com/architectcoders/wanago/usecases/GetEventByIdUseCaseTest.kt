package com.architectcoders.wanago.usecases

import com.architectcoders.wanago.data.EventsRepository
import com.architectcoders.wanago.testshared.sampleEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
internal class GetEventByIdUseCaseTest {
    @Test
    fun `when use case is invoked then events repository is called`(): Unit = runTest {
        val eventId = "1"
        val repository: EventsRepository = mock()
        val getEventByIdUseCase = GetEventByIdUseCase(repository)

        getEventByIdUseCase(eventId)

        verify(repository).getEventById(eventId)
    }

    @Test
    fun `when use case is invoked with an eventId then repository returns the event`(): Unit =
        runTest {
            val eventId = "1"
            val event = sampleEvent.copy(id = eventId)
            val repository: EventsRepository = mock {
                onBlocking { getEventById(eventId) } doReturn flowOf(event)
            }
            val getEventByIdUseCase = GetEventByIdUseCase(repository)

            val result = getEventByIdUseCase(eventId)

            assertEquals(result.first(), event)
        }
}
