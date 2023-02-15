package com.architectcoders.wanago.presentation.eventdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architectcoders.wanago.data.repository.EventRepository
import com.architectcoders.wanago.domain.model.Event
import kotlinx.coroutines.launch

class EventDetailsViewModel(private val eventRepository: EventRepository) : ViewModel() {

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event> get() = _event

    fun loadEvent(eventId: String) {
        viewModelScope.launch {
            _event.value = eventRepository.getEventById(eventId)
        }

    }
}