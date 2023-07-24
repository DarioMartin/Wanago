package com.architectcoders.wanago.usecases

import com.architectcoders.wanago.data.EventsRepository
import com.architectcoders.wanago.domain.WanagoError
import com.architectcoders.wanago.testshared.sampleEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
internal class SwitchEventFavoriteUseCaseTest {

    @Test
    fun `when use case is invoked then events repository is called`(): Unit = runTest {
        val event = sampleEvent.copy(id = "1")
        val repository: EventsRepository = mock()
        val switchEventFavoriteUseCase = SwitchEventFavoriteUseCase(repository)

        switchEventFavoriteUseCase(event)

        verify(repository).switchFavorite(event)
    }

    @Test
    fun `when repository returns error then use case returns error`(): Unit = runTest {
        val eventId = "1"
        val errorMessage = "Error message"
        val event = sampleEvent.copy(id = eventId)
        val repository: EventsRepository = mock {
            onBlocking { switchFavorite(event) } doReturn WanagoError.Unknown(errorMessage)
        }
        val switchEventFavoriteUseCase = SwitchEventFavoriteUseCase(repository)

        val result: WanagoError? = switchEventFavoriteUseCase(event)

        assertTrue(result is WanagoError.Unknown)
        assertEquals(errorMessage, (result as WanagoError.Unknown).message)
    }
}
