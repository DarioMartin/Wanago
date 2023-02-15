package com.architectcoders.wanago.data.repository

import com.architectcoders.wanago.domain.model.Event

object EventsRepository {
    private val eventList = listOf(
        Event("1", "https://loremflickr.com/320/240?lock=1", "Drake", "Sevilla"),
        Event("2", "https://loremflickr.com/320/240?lock=", "Drake", "Madrid"),
        Event("3", "https://loremflickr.com/320/240?lock=3", "Drake", "Barcelona"),
        Event("4", "https://loremflickr.com/320/240?lock=4", "Drake", "Oviedo"),
        Event("5", "https://loremflickr.com/320/240?lock=5", "Drake", "Valencia")
    )

    fun getEvents() = eventList

    fun getEventById(eventId: String): Event? = eventList.firstOrNull { it.id == eventId }
}
