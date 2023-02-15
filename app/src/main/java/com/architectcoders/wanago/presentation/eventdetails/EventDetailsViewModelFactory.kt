package com.architectcoders.wanago.presentation.eventdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.architectcoders.wanago.data.EventsRepository

@Suppress("UNCHECKED_CAST")
class EventDetailsViewModelFactory(private val eventRepository: EventsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EventDetailsViewModel(eventRepository) as T
    }
}
