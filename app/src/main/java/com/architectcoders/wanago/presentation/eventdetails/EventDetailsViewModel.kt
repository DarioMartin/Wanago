package com.architectcoders.wanago.presentation.eventdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architectcoders.wanago.data.EventsRepository
import com.architectcoders.wanago.domain.WanagoEvent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EventDetailsViewModel(private val eventRepository: EventsRepository) : ViewModel() {

    private val _event = MutableLiveData<WanagoEvent>()
    val event: LiveData<WanagoEvent> get() = _event

    fun loadEvent(eventId: String) {
        viewModelScope.launch {
            _event.value = eventRepository.getEventById(eventId).first()
        }

    }
}
