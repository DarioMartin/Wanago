package com.architectcoders.wanago.presentation.eventdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architectcoders.wanago.data.EventsRepository
import com.architectcoders.wanago.domain.WanagoEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventDetailsViewModel @Inject constructor(private val eventRepository: EventsRepository) :
    ViewModel() {

    private val _event = MutableLiveData<WanagoEvent>()
    val event: LiveData<WanagoEvent> get() = _event

    fun loadEvent(eventId: String) {
        viewModelScope.launch {
            _event.value = eventRepository.getEventById(eventId).first()
        }

    }
}
