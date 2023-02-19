package com.architectcoders.wanago.presentation.eventlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.architectcoders.wanago.data.EventsRepository
import com.architectcoders.wanago.domain.WanagoError
import com.architectcoders.wanago.domain.WanagoEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(private val eventRepository: EventsRepository) :
    ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        getEvents()
    }

    fun getEvents() {
        viewModelScope.launch {
            _state.update { _state.value.copy(loading = true) }
            val flow = eventRepository.requestNearbyEvents().cachedIn(viewModelScope)
            flow.collectLatest { pagingData -> _state.update { UiState(events = pagingData) } }
        }
    }

}

data class UiState(
    val loading: Boolean = false,
    val events: PagingData<WanagoEvent>? = null,
    val error: WanagoError? = null
)