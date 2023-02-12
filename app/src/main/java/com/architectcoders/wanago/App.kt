package com.architectcoders.wanago

import android.app.Application
import com.architectcoders.wanago.data.EventsRepository
import com.architectcoders.wanago.domain.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class App : Application() {
    lateinit var eventRepository: EventsRepository
    val listeners = mutableListOf<RepositoryListener>()

    interface RepositoryListener {
        fun onEventListUpdated()
    }

    override fun onCreate() {
        super.onCreate()
        eventRepository = EventsRepository(this)
        GlobalScope.launch(Dispatchers.IO) {
            eventRepository.requestNearbyEvents()
            for (listener in listeners)
                listener.onEventListUpdated()
        }
    }
}