package com.architectcoders.wanago.presentation.eventlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architectcoders.wanago.data.EventsRepository
import com.architectcoders.wanago.domain.WanagoEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EventListViewModel(private val eventRepository: EventsRepository) : ViewModel() {

    private val _events = MutableStateFlow(listOf<WanagoEvent>())
    val events: StateFlow<List<WanagoEvent>> = _events.asStateFlow()

    init {
        viewModelScope.launch {
            eventRepository.nearbyEvents
                .collect { events -> _events.update { events } }
        }

        getEvents()

    }

    private fun getEvents() {
        viewModelScope.launch {
            eventRepository.requestNearbyEvents()
        }
    }
}
