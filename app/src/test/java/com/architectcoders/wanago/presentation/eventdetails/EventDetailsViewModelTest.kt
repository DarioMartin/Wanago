package com.architectcoders.wanago.presentation.eventdetails

import app.cash.turbine.test
import com.architectcoders.wanago.testrules.CoroutinesTestRule
import com.architectcoders.wanago.testshared.sampleEvent
import com.architectcoders.wanago.usecases.GetEventByIdUseCase
import com.architectcoders.wanago.usecases.SwitchEventFavoriteUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
internal class EventDetailsViewModelTest {

  @get:Rule
  val coroutinesTestRule = CoroutinesTestRule()

  private val getEventByIdUseCase: GetEventByIdUseCase = mock()
  private val switchEventFavoriteUseCase: SwitchEventFavoriteUseCase = mock()

  private lateinit var eventDetailsViewModel: EventDetailsViewModel

  @Before
  fun setUp() {
    eventDetailsViewModel = EventDetailsViewModel(getEventByIdUseCase, switchEventFavoriteUseCase)
  }

  @Test
  fun `when view model is created then ui state is updated with the corresponding event`() = runTest {
    val eventId = "1"
    whenever(getEventByIdUseCase.invoke(eventId)) doReturn flowOf(sampleEvent)
    eventDetailsViewModel.onViewCreated(eventId)

    eventDetailsViewModel.state.test {
      assertEquals(UiState(loading = false), awaitItem())
      assertEquals(UiState(loading = true), awaitItem())
      assertEquals(UiState(event = sampleEvent), awaitItem())
      cancel()
    }

  }

  @Test
  fun `when fav button is clicked then switch event favorite use case is invoked`() = runTest {
    eventDetailsViewModel.onFavButtonClick(sampleEvent)
    this.advanceUntilIdle()

    verify(switchEventFavoriteUseCase).invoke(sampleEvent)
  }

}
