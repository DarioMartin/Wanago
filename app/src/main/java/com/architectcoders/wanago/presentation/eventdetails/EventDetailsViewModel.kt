package com.architectcoders.wanago.presentation.eventdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architectcoders.wanago.data.EventsRepository
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

    private val _event = MutableStateFlow<WanagoEvent?>(null)
    val event: StateFlow<WanagoEvent?> = _event.asStateFlow()

    fun loadEvent(eventId: String) {
        viewModelScope.launch {
            eventRepository.getEventById(eventId).collect { event ->
                _event.update { event }
            }
        }
    }
}
