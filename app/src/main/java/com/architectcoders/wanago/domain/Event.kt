package com.architectcoders.wanago.domain

import com.architectcoders.wanago.data.server.Start
import com.architectcoders.wanago.data.server.Status

data class Event(
    val id: String,
    val name: String,
    val imageUrl: String,
    val venue: String,
    )