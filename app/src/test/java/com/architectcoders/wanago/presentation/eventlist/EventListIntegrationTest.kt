package com.architectcoders.wanago.presentation.eventlist

import app.cash.turbine.test
import com.architectcoders.wanago.data.EventsRepository
import com.architectcoders.wanago.data.RegionRepository
import com.architectcoders.wanago.presentation.FakeLocalDataSource
import com.architectcoders.wanago.presentation.FakeLocationDataSource
import com.architectcoders.wanago.presentation.FakePermissionChecker
import com.architectcoders.wanago.presentation.FakeRemoteDataSource
import com.architectcoders.wanago.testrules.CoroutinesTestRule
import com.architectcoders.wanago.usecases.GetNearbyEventsUseCase
import com.architectcoders.wanago.usecases.SwitchEventFavoriteUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class EventListIntegrationTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `data is loaded on start`() = runTest {
        val viewModel = buildViewModel()
        viewModel.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(loading = true), awaitItem())
            val finalState = awaitItem()
            assertEquals(false, finalState.loading)
            assertNotNull(finalState.events)
            cancel()
        }
    }

    private fun buildViewModel(): EventListViewModel {
        val fakePermissionChecker = FakePermissionChecker()
        val fakeLocationDataSource = FakeLocationDataSource()
        val fakeRegionRepository = RegionRepository(fakeLocationDataSource, fakePermissionChecker)

        val fakeLocalDataSource = FakeLocalDataSource()
        val fakeRemoteDataSource = FakeRemoteDataSource()
        val eventsRepository =
            EventsRepository(fakeRegionRepository, fakeLocalDataSource, fakeRemoteDataSource)

        val getNearbyEventsUseCase = GetNearbyEventsUseCase(eventsRepository)
        val switchEventFavoriteUseCase = SwitchEventFavoriteUseCase(eventsRepository)

        return EventListViewModel(getNearbyEventsUseCase, switchEventFavoriteUseCase)
    }
}
