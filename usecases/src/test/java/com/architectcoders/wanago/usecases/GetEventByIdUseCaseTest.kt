package com.architectcoders.wanago.usecases

import com.architectcoders.wanago.data.EventsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
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
}
