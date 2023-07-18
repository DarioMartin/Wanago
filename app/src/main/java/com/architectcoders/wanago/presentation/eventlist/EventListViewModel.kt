package com.architectcoders.wanago.presentation.eventlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.architectcoders.wanago.domain.WanagoError
import com.architectcoders.wanago.domain.WanagoEvent
import com.architectcoders.wanago.usecases.GetNearbyEventsUseCase
import com.architectcoders.wanago.usecases.SwitchEventFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(
    private val getNearbyEventsUseCase: GetNearbyEventsUseCase,
    private val switchEventFavoriteUseCase: SwitchEventFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        getEvents()
    }

    fun onSwipeToRefresh() {
        getEvents()
    }

    fun onFavButtonClick(event: WanagoEvent) {
        viewModelScope.launch {
            switchEventFavoriteUseCase(event)
        }
    }

    private fun getEvents() {
        viewModelScope.launch {
            _state.update { _state.value.copy(loading = true) }
            getNearbyEventsUseCase.invoke(this).collectLatest { pagingData ->
                _state.update { UiState(events = pagingData) }
            }
        }
    }
}

data class UiState(
    val loading: Boolean = false,
    val events: PagingData<WanagoEvent>? = null,
    val error: WanagoError? = null
)
