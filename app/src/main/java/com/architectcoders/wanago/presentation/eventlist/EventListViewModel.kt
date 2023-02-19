package com.architectcoders.wanago.presentation.eventlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architectcoders.wanago.domain.WanagoError
import com.architectcoders.wanago.domain.WanagoEvent
import com.architectcoders.wanago.domain.toError
import com.architectcoders.wanago.usecases.GetNearbyEventsUseCase
import com.architectcoders.wanago.usecases.RequestNearbyEventsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(
    getNearbyEventsUseCase: GetNearbyEventsUseCase,
    private val requestNearbyEventsUseCase: RequestNearbyEventsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getNearbyEventsUseCase()
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { events -> _state.update { UiState(events = events) } }
        }

        getEvents()
    }

    fun getEvents(forceUpdate: Boolean = false) {
        viewModelScope.launch {
            _state.update { _state.value.copy(loading = true) }
            val error = requestNearbyEventsUseCase(forceUpdate)
            _state.update { _state.value.copy(loading = false, error = error) }
        }
    }
}

data class UiState(
    val loading: Boolean = false,
    val events: List<WanagoEvent>? = null,
    val error: WanagoError? = null
)

