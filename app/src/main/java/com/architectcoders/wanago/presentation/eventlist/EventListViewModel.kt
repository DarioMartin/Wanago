package com.architectcoders.wanago.presentation.eventlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architectcoders.wanago.data.repository.EventsRepository
import com.architectcoders.wanago.domain.model.Event
import kotlinx.coroutines.launch

class EventListViewModel(private val eventRepository: EventsRepository) : ViewModel() {
    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> get() = _events

    init {
        getEvents()
    }

    private fun getEvents() {
        viewModelScope.launch {
            _events.value = eventRepository.getEvents()
        }
    }
}
