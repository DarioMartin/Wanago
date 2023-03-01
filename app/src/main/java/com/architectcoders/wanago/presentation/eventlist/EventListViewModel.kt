package com.architectcoders.wanago.presentation.eventlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.architectcoders.wanago.domain.WanagoError
import com.architectcoders.wanago.domain.WanagoEvent
import com.architectcoders.wanago.usecases.GetNearbyEventsUseCase
import com.architectcoders.wanago.usecases.SwitchEventFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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

    fun getEvents() {
        viewModelScope.launch {
            _state.update { _state.value.copy(loading = true) }
            getNearbyEventsUseCase.invoke().cachedIn(this).collectLatest { pagingData ->
                _state.update { UiState(events = pagingData) }
            }
        }
    }

    fun switchFavorite(event: WanagoEvent) {
        viewModelScope.launch {
            switchEventFavoriteUseCase(event)
        }
    }

}

data class UiState(
    val loading: Boolean = false,
    val events: PagingData<WanagoEvent>? = null,
    val error: WanagoError? = null
)

