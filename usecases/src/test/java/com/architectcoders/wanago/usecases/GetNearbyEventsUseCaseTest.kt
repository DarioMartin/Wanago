package com.architectcoders.wanago.usecases

import com.architectcoders.wanago.data.EventsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
internal class GetNearbyEventsUseCaseTest {

    @Test
    fun `when use case is invoked then events repository is called`(): Unit = runTest {
        val repository: EventsRepository = mock()
        val getNearbyEventsUseCase = GetNearbyEventsUseCase(repository)
        val testScope = TestScope()

        getNearbyEventsUseCase(testScope)

        verify(repository).requestNearbyEvents(testScope)
    }

}
