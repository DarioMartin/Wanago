package com.architectcoders.wanago.presentation.eventdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.architectcoders.wanago.data.repository.EventRepository

class EventDetailsViewModelFactory(private val eventRepository: EventRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EventDetailsViewModel(eventRepository) as T
    }
}