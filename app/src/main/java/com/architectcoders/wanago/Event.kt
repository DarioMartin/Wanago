package com.architectcoders.wanago

import com.architectcoders.wanago.data.server.Start
import com.architectcoders.wanago.data.server.Status

data class Event(
    val name: String,
    val imageUrl: String,
    val venue: String,
    val type: String
    )