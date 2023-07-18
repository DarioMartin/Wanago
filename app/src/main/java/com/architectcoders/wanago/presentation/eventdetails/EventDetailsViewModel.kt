package com.architectcoders.wanago.presentation.eventdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architectcoders.wanago.domain.WanagoError
import com.architectcoders.wanago.domain.WanagoEvent
import com.architectcoders.wanago.usecases.GetEventByIdUseCase
import com.architectcoders.wanago.usecases.SwitchEventFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventDetailsViewModel @Inject constructor(
    private val getEventByIdUseCase: GetEventByIdUseCase,
    private val switchEventFavoriteUseCase: SwitchEventFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun onViewCreated(eventId: String) {
        viewModelScope.launch {
            _state.update { _state.value.copy(loading = true) }
            getEventByIdUseCase(eventId).collect { event ->
                _state.update { UiState(event = event) }
            }
        }
    }

    fun onFavButtonClick(event: WanagoEvent) {
        viewModelScope.launch {
            switchEventFavoriteUseCase(event)
        }
    }
}

data class UiState(
    val loading: Boolean = false,
    val event: WanagoEvent? = null,
    val error: WanagoError? = null
)
