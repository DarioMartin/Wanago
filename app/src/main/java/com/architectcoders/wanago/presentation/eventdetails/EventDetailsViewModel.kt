package com.architectcoders.wanago.presentation.eventdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architectcoders.wanago.data.EventsRepository
import com.architectcoders.wanago.domain.WanagoError
import com.architectcoders.wanago.domain.WanagoEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventDetailsViewModel @Inject constructor(private val eventRepository: EventsRepository) :
    ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun loadEvent(eventId: String) {
        viewModelScope.launch {
            _state.update { _state.value.copy(loading = true) }
            eventRepository.getEventById(eventId).collect { event ->
                _state.update { UiState(event = event) }
            }
        }
    }
}

data class UiState(
    val loading: Boolean = false,
    val event: WanagoEvent? = null,
    val error: WanagoError? = null
)