package com.architectcoders.wanago.presentation.eventdetails

import app.cash.turbine.test
import com.architectcoders.wanago.data.EventsRepository
import com.architectcoders.wanago.data.RegionRepository
import com.architectcoders.wanago.domain.WanagoEvent
import com.architectcoders.wanago.presentation.FakeLocalDataSource
import com.architectcoders.wanago.presentation.FakeLocationDataSource
import com.architectcoders.wanago.presentation.FakePermissionChecker
import com.architectcoders.wanago.presentation.FakeRemoteDataSource
import com.architectcoders.wanago.presentation.defaultEvents
import com.architectcoders.wanago.testrules.CoroutinesTestRule
import com.architectcoders.wanago.testshared.sampleEvent
import com.architectcoders.wanago.usecases.GetEventByIdUseCase
import com.architectcoders.wanago.usecases.SwitchEventFavoriteUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
internal class EventDetailsIntegrationTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `favorite is updated in local data source`() = runTest {
        val viewModel = buildViewModel(defaultEvents, defaultEvents)
        viewModel.onViewCreated("2")
        viewModel.onFavButtonClick(defaultEvents.first { it.id == "2" })

        viewModel.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(loading = true), awaitItem())
            assertEquals(
                UiState(event = sampleEvent.copy(id = "2", isFavorite = false)), awaitItem()
            )
        }

    }

    private fun buildViewModel(
        localData: List<WanagoEvent>,
        remoteData: List<WanagoEvent>
    ): EventDetailsViewModel {
        val fakePermissionChecker = FakePermissionChecker()
        val fakeLocationDataSource = FakeLocationDataSource()
        val fakeRegionRepository = RegionRepository(fakeLocationDataSource, fakePermissionChecker)

        val fakeLocalDataSource = FakeLocalDataSource().apply { events.value = localData }
        val fakeRemoteDataSource = FakeRemoteDataSource().apply { events = remoteData }
        val eventsRepository =
            EventsRepository(fakeRegionRepository, fakeLocalDataSource, fakeRemoteDataSource)

        val getEventsUseCase = GetEventByIdUseCase(eventsRepository)
        val switchEventFavoriteUseCase = SwitchEventFavoriteUseCase(eventsRepository)

        return EventDetailsViewModel(getEventsUseCase, switchEventFavoriteUseCase)
    }
}
