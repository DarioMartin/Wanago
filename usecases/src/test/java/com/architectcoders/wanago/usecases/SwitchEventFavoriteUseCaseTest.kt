package com.architectcoders.wanago.usecases

import com.architectcoders.wanago.data.EventsRepository
import com.architectcoders.wanago.testshared.sampleEvent
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify

internal class SwitchEventFavoriteUseCaseTest {

    @Test
    fun `when use case is invoked then events repository is called`(): Unit = runBlocking {
        val event = sampleEvent.copy(id = "1")
        val repository: EventsRepository = mock()
        val switchEventFavoriteUseCase = SwitchEventFavoriteUseCase(repository)

        switchEventFavoriteUseCase(event)

        verify(repository).switchFavorite(event)
    }
}
