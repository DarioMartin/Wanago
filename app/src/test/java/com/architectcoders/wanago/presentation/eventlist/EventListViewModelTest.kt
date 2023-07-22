package com.architectcoders.wanago.presentation.eventlist

import androidx.paging.PagingData
import app.cash.turbine.test
import com.architectcoders.wanago.testrules.CoroutinesTestRule
import com.architectcoders.wanago.testshared.sampleEvent
import com.architectcoders.wanago.usecases.GetNearbyEventsUseCase
import com.architectcoders.wanago.usecases.SwitchEventFavoriteUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
internal class EventListViewModelTest {

  @get:Rule
  val coroutinesTestRule = CoroutinesTestRule()

  private val getNearbyEventsUseCase: GetNearbyEventsUseCase = mock()
  private val switchEventFavoriteUseCase: SwitchEventFavoriteUseCase = mock()
  private val events = PagingData.from(listOf(sampleEvent.copy(id = "1")))

  @Test
  fun `when view model is initialized then ui state is updated with nearby events`() = runTest {
    val eventListViewModel = buildViewModel()

    eventListViewModel.state.test {
      assertEquals(UiState(), awaitItem())
      assertEquals(UiState(loading = true), awaitItem())
      assertEquals(UiState(events = events), awaitItem())
      cancel()
    }
  }

  @Test
  fun `when swipe to refresh is triggered then get nearby events use case is invoked`() = runTest {
    val eventListViewModel = buildViewModel()

    eventListViewModel.onSwipeToRefresh()
    this.advanceUntilIdle()

    verify(getNearbyEventsUseCase, times(2)).invoke(any())
  }

  @Test
  fun `when fav button is clicked then switch event favorite use case is invoked`() = runTest {
    val eventListViewModel = buildViewModel()

    eventListViewModel.onFavButtonClick(sampleEvent)
    this.advanceUntilIdle()

    verify(switchEventFavoriteUseCase).invoke(sampleEvent)
  }

  private suspend fun buildViewModel(): EventListViewModel {
    whenever(getNearbyEventsUseCase(any())) doReturn flowOf(events)
    return EventListViewModel(getNearbyEventsUseCase, switchEventFavoriteUseCase)
  }

}
