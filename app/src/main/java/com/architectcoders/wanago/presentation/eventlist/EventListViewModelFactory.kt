package com.architectcoders.wanago.presentation.eventlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.architectcoders.wanago.data.EventsRepository

@Suppress("UNCHECKED_CAST")
class EventListViewModelFactory(private val eventRepository: EventsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EventListViewModel(eventRepository) as T
    }
}
